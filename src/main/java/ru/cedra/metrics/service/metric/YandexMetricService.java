package ru.cedra.metrics.service.metric;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import ru.cedra.metrics.config.ApplicationProperties;
import ru.cedra.metrics.domain.*;
import ru.cedra.metrics.service.ChatUserService;
import ru.cedra.metrics.service.bot.ChatStateService;
import ru.cedra.metrics.service.dto.Counter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by tignatchenko on 24/04/17.
 */
@Service
public class YandexMetricService {

    @Autowired
    ApplicationProperties applicationProperties;
    @Autowired
    ChatStateService chatStateService;
    @Autowired
    ChatUserService chatUserService;


    private static final String REPORT_PREFIX="https://api-metrika.yandex.ru/stat/v1/data?";

    private final Logger log = LoggerFactory.getLogger(YandexMetricService.class);

    public List<Counter> getCounts(Long chatId) {
        String token = getToken(chatId);
        String url = String.format("https://api-metrika.yandex.ru/management/v1/counters?oauth_token=%s",
                                   token);
        List<Counter> counters = new ArrayList<>();
        try {
            String content = Request.Get(url).execute().returnContent().asString();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(content);
            int count = jsonNode.get("rows").asInt();
            if (count > 0) {
                ArrayNode metricsArray = (ArrayNode) jsonNode.get("counters");
                for (JsonNode node: metricsArray) {
                    Counter counter = new Counter();
                    counter.setName(node.get("name").asText());
                    counter.setId(node.get("id").asInt());
                    counters.add(counter);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return counters;
    }

    public List<Pair> getGoals(Long chatId, String counterId) {
        String token = getToken(chatId);
        String url = String.format(
            "https://api-metrika.yandex.ru/management/v1/counter/%s/goals?oauth_token=%s",
            counterId,
            token);
        List<Pair> goals = new ArrayList<>();
        try {
            String content = Request.Get(url).execute().returnContent().asString();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(content);
            ArrayNode goalsArray = (ArrayNode)jsonNode.get("goals");
            for (JsonNode node: goalsArray) {
                goals.add(Pair.of(node.get("id").asInt(), node.get("name").asText()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return goals;
    }

    public void fillStats (Long chatId, CommonStats commonStats) {
        String token = getToken(chatId);
        Integer countId = 0;
        try {
            countId = Integer.parseInt(commonStats.getMetric().getCountName());
        } catch (Exception ex) {
        }
        fillDirectStats(chatId, commonStats);

        String url = String.format(REPORT_PREFIX+"metrics=ym:s:goal%susers,ym:s:users&id=%s&oauth_token=%s" +
                                       "&date1=today",
                                   commonStats.getMetric().getGoalId(),
                                   "" + countId,
                                   token);



        try {
            String content = Request.Get(url).execute().returnContent().asString();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(content);
            JsonNode data = jsonNode.get("data");
            if (data.isArray()) {
                ArrayNode dataArray = (ArrayNode) data;
                if (dataArray.size() > 0) {
                    JsonNode metrics = dataArray.get(0).get("metrics");
                    if (metrics.isArray()) {
                        ArrayNode arrayNode = (ArrayNode) metrics;
                        if (arrayNode.size() > 0) {
                            commonStats.setGoalAchievements((int)metrics.get(0).asDouble());
                            commonStats.setVisits((int)metrics.get(1).asDouble());
                        }
                    }
                } else {
                    commonStats.setGoalAchievements(0);
                    commonStats.setVisits(0);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fillDirectStats (Long chatId, CommonStats commonStats) {
        String token = getToken(chatId);

        ChatUser chatUser = chatUserService.getChatUser(chatId);
        String[] campaigns = commonStats.getMetric().getCampainIds().split(" ");

        ReportDefinition reportDefinition = new ReportDefinition();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        String fromReportDate = df.format(commonStats.getMetric().getReportFromDate());

        reportDefinition.setDateRangeType(DateRangeTypeEnum.CUSTOM_DATE);
        SelectionCriteria selectionCriteria = new SelectionCriteria();
        selectionCriteria.setDateTo(reportDate);
        selectionCriteria.setDateFrom(fromReportDate);
        FilterItem filterItem = new FilterItem();
        filterItem.setField(FieldEnum.CAMPAIGN_ID);
        filterItem.setOperator(FilterOperatorEnum.IN);
        filterItem.setValues(Lists.newArrayList(campaigns));
        selectionCriteria.setFilter(Lists.newArrayList(filterItem));
        List<OrderBy> orderBies = new ArrayList<>();
        OrderBy orderBy = new OrderBy();
        orderBy.setField(FieldEnum.CAMPAIGN_ID);
        orderBies.add(orderBy);
        reportDefinition.setOrderBy(orderBies);
        reportDefinition.setReportName("REPORT"+ UUID.randomUUID());
        reportDefinition.setReportType(ReportTypeEnum.CUSTOM_REPORT);
        reportDefinition.setFormat(FormatEnum.TSV);
        reportDefinition.setIncludeDiscount(YesNoEnum.NO);
        reportDefinition.setIncludeVAT(YesNoEnum.NO);
        reportDefinition.setFieldNames(Lists.newArrayList(FieldEnum.AVG_CPC, FieldEnum.COST));
        reportDefinition.setSelectionCriteria(selectionCriteria);
        StringWriter writer = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ReportDefinition.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.marshal(reportDefinition, writer);
            StringEntity reportRequest = new StringEntity(writer.getBuffer().toString());
            String report = Request.Post(getDirectReportLink()).addHeader("Authorization", "Bearer "+token)
                                     .body(reportRequest).execute()
                                     .returnContent().asString();
            String[] strings = report.split("\n");
            if (strings.length <= 3) {
                return;
            } else {
                String[] vals = strings[2].split(" ");
                commonStats.setClickCost(Float.parseFloat(vals[0]));
                commonStats.setBudget(Float.parseFloat(vals[1]));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }



    }


    public String getDirectReportLink (){
        return "https://api-sandbox.direct.yandex.com/v5/reports";
    }
    public String getTokenLink (){
        return "https://oauth.yandex.ru/authorize?" +
            "response_type=code&client_id="+applicationProperties.getYaClientId();
    }

    private String getToken(Long chatId) {
        String token = chatUserService.getChatUser(chatId).getYaToken();
        return token;
    }


    public SendMessage handleYandexToken (Long chatId, String responseCode) {
        SendMessage sendMessage = new SendMessage().setChatId(chatId);
        try {
            String content = Request.Post("https://oauth.yandex.ru/token").bodyForm(
                new BasicNameValuePair("grant_type", "authorization_code"),
                new BasicNameValuePair("code", responseCode),
                new BasicNameValuePair("client_id", applicationProperties.getYaClientId()),
                new BasicNameValuePair("client_secret", applicationProperties.getYaClientSecret())
            ).execute().returnContent().asString();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(content);
            chatUserService.saveYandexToken(chatId, jsonNode.get("access_token").textValue());
            chatStateService.updateChatStep(0, chatId);
        } catch (Exception e) {
            log.error(e.getMessage());
            sendMessage.setText("Произошла ошибка при получении токена");
            return sendMessage;
        }

        sendMessage.setText("Ваш токен успешно обновлен");
        return sendMessage;
    }

}

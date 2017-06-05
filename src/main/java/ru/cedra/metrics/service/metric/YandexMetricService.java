package ru.cedra.metrics.service.metric;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.Lists;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import ru.cedra.metrics.config.ApplicationProperties;
import ru.cedra.metrics.domain.ChatUser;
import ru.cedra.metrics.domain.CommonStats;
import ru.cedra.metrics.domain.DateRangeTypeEnum;
import ru.cedra.metrics.domain.FieldEnum;
import ru.cedra.metrics.domain.FilterItem;
import ru.cedra.metrics.domain.FilterOperatorEnum;
import ru.cedra.metrics.domain.FormatEnum;
import ru.cedra.metrics.domain.Metric;
import ru.cedra.metrics.domain.OrderBy;
import ru.cedra.metrics.domain.ReportDefinition;
import ru.cedra.metrics.domain.ReportTypeEnum;
import ru.cedra.metrics.domain.SelectionCriteria;
import ru.cedra.metrics.domain.User;
import ru.cedra.metrics.domain.YesNoEnum;
import ru.cedra.metrics.repository.ChatStateRepository;
import ru.cedra.metrics.repository.CommonStatsRepository;
import ru.cedra.metrics.repository.MetricRepository;
import ru.cedra.metrics.service.ChatUserService;
import ru.cedra.metrics.service.MailService;
import ru.cedra.metrics.service.bot.ChatStateService;
import ru.cedra.metrics.service.bot.MetricService;
import ru.metrika4j.ApiFactory;
import ru.metrika4j.MetrikaApi;
import ru.metrika4j.MetrikaDate;
import ru.metrika4j.Report;
import ru.metrika4j.ReportBuilder;
import ru.metrika4j.ReportItem;
import ru.metrika4j.Reports;
import ru.metrika4j.entity.Counter;
import ru.metrika4j.json.jackson.JacksonMapper;
import springfox.documentation.spring.web.json.Json;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;


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
        MetrikaApi api = ApiFactory.createMetrikaAPI(token, new JacksonMapper());
        Counter[] counters = api.getCounters();

        return Lists.newArrayList(counters);
    }

    public void fillStats (Long chatId, CommonStats commonStats) {
        String token = getToken(chatId);
        MetrikaApi api = ApiFactory.createMetrikaAPI(token, new JacksonMapper());
        Integer countId = 0;
        try {
            countId = Integer.parseInt(commonStats.getMetric().getCountName());
        } catch (Exception ex) {
        }

        ReportBuilder builder = api.makeReportBuilder(Reports.trafficSummary, countId);
        Report report = builder.withDateFrom(MetrikaDate.yesterday()).withDateTo(MetrikaDate.today()).build();
        ReportItem[] items = report.getData();
        if (items.length > 0) {
            ReportItem item = items[0];
            //commonStats.setDate(Timestamp.valueOf(LocalDate.parse(item.getString("date"), DateTimeFormatter.ofPattern("yyyyMMdd")).atStartOfDay()));
            commonStats.setVisits(item.getInt("visitors"));
        }
        fillDirectStats(chatId, commonStats);

        String url = String.format(REPORT_PREFIX+"metrics=ym:s:goal%susers&id=%s&oauth_token=%s" +
                                       "&date1=yesterday&date2=today",
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
                        }
                    }
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

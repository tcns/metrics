package ru.cedra.metrics.service.bot;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons
    .InlineKeyboardButton;
import ru.cedra.metrics.domain.ChatState;
import ru.cedra.metrics.domain.ChatStates;
import ru.cedra.metrics.domain.Commands;
import ru.cedra.metrics.domain.CommonStats;
import ru.cedra.metrics.domain.Metric;
import ru.cedra.metrics.repository.MetricRepository;
import ru.cedra.metrics.service.ChatUserService;
import ru.cedra.metrics.service.metric.CommonStatsService;
import ru.cedra.metrics.service.metric.YandexMetricService;
import ru.cedra.metrics.service.util.TaskUtil;
import ru.metrika4j.entity.Counter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Created by tignatchenko on 24/04/17.
 */
@Service
public class MetricService {
    @Autowired
    MetricRepository metricRepository;

    @Autowired
    ChatStateService chatStateService;

    @Autowired
    ChatUserService chatUserService;

    @Autowired
    YandexMetricService yandexMetricService;

    @Autowired
    MetricBot metricBot;

    @Autowired
    TaskScheduler taskScheduler;

    @Autowired
    CalcService calcService;

    @Autowired
    CommonStatsService commonStatsService;

    public SendMessage initConversation (Long chatId) {

        List<Counter> counts = yandexMetricService.getCounts(chatId);
        SendMessage message = new SendMessage()
            .setChatId(chatId)
            .setReplyMarkup(getKeyBoard(counts.stream().map(a-> new ImmutablePair<>(a.getId(), a.getName())).collect(Collectors.toList())))
            .setText(ChatStates.states.get(1));
        Metric metric = new Metric();
        metric.setChatUser(chatUserService.getChatUser(chatId));
        metric = metricRepository.save(metric);
        chatStateService.updateChatStep(1, chatId, metric.getId()+"");
        return message;
    }

    public SendMessage initEdition (Long chatId, Integer step, String metricId) {
        SendMessage message = new SendMessage()
            .setChatId(chatId)
            .setText("Введите новое значение параметра");
        chatStateService.updateChatStep(step, chatId, Commands.EDIT_ONE_PARAM_FINAL + metricId);
        return message;
    }

    public SendMessage handleInput(String input, Long chatId) {
        ChatState chatState = chatStateService.getCurrentChatState(chatId);
        int chatStep = chatState.getStep();
        SendMessage sendMessage;
        Long metricId = 0L;
        boolean isEdit = false;
        String editType = "";
        if (chatState.getData().startsWith(Commands.EDIT_ONE_PARAM_FINAL)) {
            isEdit = true;
            editType = Commands.EDIT_ONE_PARAM_FINAL;
            try {
                metricId = Long.parseLong(chatState.getData().substring(Commands.EDIT_ONE_PARAM_FINAL

                                                                            .length()));
            } catch (Exception ex){}
        } else  if (chatState.getData().startsWith(Commands.DEALS_EDIT_FINAL)){
            isEdit = true;
            editType = Commands.DEALS_EDIT_FINAL;
            try {
                metricId = Long.parseLong(chatState.getData().substring(Commands.DEALS_EDIT_FINAL

                                                                            .length()));
            } catch (Exception ex){}
        } else {
            try {
                metricId = Long.parseLong(chatState.getData());
            } catch (Exception ex){}
        }


        Metric metric = metricRepository.findOne(metricId);
        try {
            switch (chatStep) {
                case ChatStates.COUNT_STEP: metric.setCountName(input); break;
                case ChatStates.NAME_STEP: metric.setName(input); break;
                case ChatStates.GOAL_STEP: metric.setGoalId(input); break;
                case ChatStates.INCOME_STEP: metric.setClearIncome(Integer.parseInt(input)); break;
                case ChatStates.RENT_STEP: metric.setRent(Float.parseFloat(input)); break;
                case ChatStates.SALE_CONVERSION_STEP: metric.setSaleConversion(Float.parseFloat(input)); break;
                case ChatStates.AVG_CHECK_STEP: metric.setAvgCheck(Float.parseFloat(input)); break;
                case ChatStates.SITE_CONVERSION_STEP: metric.setSiteConversion(Float.parseFloat(input)); break;
                case ChatStates.CLICK_PRICE_STEP: metric.setClickPrice(Float.parseFloat(input)); break;
                case ChatStates.ASK_TIME_STEP:
                    Integer time = Integer.parseInt(input);
                    if (time < 0 || time > 23) throw  new Exception();
                    metric.setAskTime(input);
                    break;
                case ChatStates.REPORT_TIME_STEP:
                    Integer timeReport = Integer.parseInt(input);
                    if (timeReport < 0 || timeReport > 23) throw  new Exception();
                    metric.setReportTime(input);
                    break;
                case ChatStates.DEALS_EDIT:
                    String[] callsAndDeals = input.split(" ");
                    commonStatsService.updateTodayCallsAndDeals(
                        Integer.parseInt(callsAndDeals[0]),
                        Integer.parseInt(callsAndDeals[1]),
                        metricId
                    );
                    break;
            }
        } catch (Exception ex) {
            sendMessage = new SendMessage().setText("Вы ввели невалидное значение").
                setChatId(chatId);
            return sendMessage;
        }

        if (chatStep==ChatStates.METRIC_COMPLETE - 1 ||
            Commands.EDIT_ONE_PARAM_FINAL.equals(editType)) {
            chatStateService.updateChatStep(0, chatId);
            metric.setMonthDeals(calcService.calcMonthDeals(metric));
            if (!isEdit) {
                metric.setReportFromDate(new Date(new java.util.Date().getTime()));
            }
            metric.setMonthCount(calcService.calcMonthVisits(
                metric.getMonthDeals(),
                metric.getSaleConversion(),
                metric.getSiteConversion()
            ));
            TaskUtil.deleteTask(taskScheduler, metricId);
            TaskUtil.registerTask(commonStatsService, metricBot, taskScheduler, metric);
            sendMessage = new SendMessage().setText(ChatStates.states.get(ChatStates.METRIC_COMPLETE))
                .setChatId(chatId);
            if (Commands.EDIT_ONE_PARAM_FINAL.equals(editType)) {
                sendMessage.setText("Значение успешно обновлено!");
            }
        } else if (Commands.DEALS_EDIT_FINAL.equals(editType)) {
            chatStateService.updateChatStep(0, chatId);
            sendMessage = new SendMessage().setText("Значения успешно обновлены!").setChatId(chatId);
        } else {
            sendMessage = chatStateService.updateStepAndGetMessage(chatStep + 1, chatId, metricId+"");
        }

        metricRepository.save(metric);
        return sendMessage;


    }


    public SendMessage getMetricDefinition (Long chatId, Long metricId) {
            Metric metric = metricRepository.findOne(metricId);
            String metricDefinition = "";
            if (!metric.getChatUser().getTelegramChatId().equals(chatId)) {
                metricDefinition = "Метрика не найдена";
            } else {
                metricDefinition = metric.toString();
            }
            SendMessage sendMessage = new SendMessage()
                .setChatId(chatId)
                .setText(metricDefinition);
            return sendMessage;
    }

    public SendMessage getMetricList(Long chatId, String command) {
        Set<Metric> metrics = metricRepository.findByChatUser_TelegramChatId(chatId);
        InlineKeyboardMarkup replyMarkup = new InlineKeyboardMarkup();
        final List<List<InlineKeyboardButton>> keyboardButtons = new ArrayList<>(metrics.size());

        metrics.stream().forEach((metric) -> {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(metric.getName()==null?"Не указано":metric.getName());
            inlineKeyboardButton.setCallbackData(command+metric.getId());
            keyboardButtons.add(Collections.singletonList(inlineKeyboardButton));
        });

        replyMarkup.setKeyboard(keyboardButtons);
        return new SendMessage().setText("список отчетов")
                                .setChatId(chatId)
                                .setReplyMarkup(replyMarkup);
    }


    private InlineKeyboardMarkup getKeyBoard(List<Pair> counts) {
        InlineKeyboardMarkup replyMarkup = new InlineKeyboardMarkup();
        final List<List<InlineKeyboardButton>> keyboardButtons = new ArrayList<>(counts.size());

        counts.stream().forEach((count) -> {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(""+count.getValue());
            inlineKeyboardButton.setCallbackData("" + count.getKey());
            keyboardButtons.add(Collections.singletonList(inlineKeyboardButton));
        });

        replyMarkup.setKeyboard(keyboardButtons);
        return replyMarkup;
    }

    private InlineKeyboardMarkup getFieldKeyBoard(Long metricId) {
        InlineKeyboardMarkup replyMarkup = new InlineKeyboardMarkup();
        final List<List<InlineKeyboardButton>> keyboardButtons = new ArrayList<>(ChatStates.states.keySet().size());

        ChatStates.states.forEach((key, value) -> {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(value);
            inlineKeyboardButton.setCallbackData(
                Commands.EDIT_ONE_PARAM + metricId + "-" + key);
            if (ChatStates.paramStates.contains(key)) {
                keyboardButtons.add(Collections.singletonList(inlineKeyboardButton));
            }
        });

        replyMarkup.setKeyboard(keyboardButtons);
        return replyMarkup;
    }

    public List<Metric> findAll () {
        return metricRepository.findAll();
    }

    public SendMessage editMetric (Long chatId, Long metricId) {
        return new SendMessage()
            .setChatId(chatId)
            .setText("Редактирование метрики")
            .setReplyMarkup(getFieldKeyBoard(metricId));
    }

    public SendMessage editDeals (Long chatId, Long metricId) {
        chatStateService.updateChatStep(ChatStates.DEALS_EDIT, chatId,
                                        Commands.DEALS_EDIT_FINAL + metricId);

        return new SendMessage()
            .setChatId(chatId)
            .setText(ChatStates.states.get(ChatStates.DEALS_EDIT));
    }

    public SendMessage deleteMetric (Long chatId, Long metricId) {
        Metric metric = metricRepository.getOne(metricId);
        String name = "";
        try {
            name = metric.getName();
        } catch (Exception e){}
        metricRepository.delete(metricId);
        TaskUtil.deleteTask(taskScheduler, metricId);
        return new SendMessage()
            .setChatId(chatId)
            .setText("Метрика " + name + " удалена" );
    }
}

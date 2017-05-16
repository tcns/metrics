package ru.cedra.metrics.service.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ru.cedra.metrics.config.ApplicationProperties;
import ru.cedra.metrics.domain.ChatStates;
import ru.cedra.metrics.domain.ChatUser;
import ru.cedra.metrics.domain.Commands;
import ru.cedra.metrics.service.ChatUserService;
import ru.cedra.metrics.service.UserService;
import ru.cedra.metrics.service.metric.YandexMetricService;


/**
 * Created by tignatchenko on 22/04/17.
 */
@Service
public class MetricBot extends TelegramLongPollingBot {
    @Autowired
    ApplicationProperties applicationProperties;
    @Autowired
    MetricService metricService;
    @Autowired
    UserService userService;
    @Autowired
    ChatUserService chatUserService;
    @Autowired
    ChatStateService chatStateService;
    @Autowired
    YandexMetricService yandexMetricService;


    public void sendMessageExternal (SendMessage sendMessage) {
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            int chatStep = chatStateService.getCurrentStep(chatId);
            SendMessage message = new SendMessage();
            if (callbackData.startsWith(Commands.GET_METRIC)) {
                message = metricService.getMetricDefinition(chatId,
                                                            Long.parseLong(callbackData.substring(Commands.GET_METRIC.length())));
            } else if (callbackData.startsWith(Commands.DELETE_ONE_METRIC)) {
                message = metricService.deleteMetric(chatId,
                                                            Long.parseLong(callbackData.substring(Commands.DELETE_ONE_METRIC.length())));
            } else if (callbackData.startsWith(Commands.EDIT_ONE_METRIC)) {
                message = metricService.editMetric(chatId,
                                                     Long.parseLong(callbackData.substring(Commands.EDIT_ONE_METRIC.length())));
            } else if (callbackData.startsWith(Commands.EDIT_ONE_PARAM)) {
                String signature = callbackData.substring(Commands.EDIT_ONE_PARAM.length());
                String[] kv = signature.split("-");
                message = metricService.initEdition(chatId, Integer.parseInt(kv[1]), kv[0]);
            } else if (callbackData.startsWith(Commands.DEALS_EDIT)) {
                message = metricService.editDeals(chatId,
                                                  Long.parseLong(callbackData.substring(Commands.DEALS_EDIT.length())));
            } else  {
                switch (chatStep) {
                    case ChatStates.COUNT_STEP:
                        message = metricService.handleInput(callbackData, chatId);
                        break;
                }
            }

            try {
                sendMessage(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.hasMessage() && update.getMessage().hasText()) {

            Long chatId = update.getMessage().getChatId();
            SendMessage message = new SendMessage();
            String input = update.getMessage().getText();
            switch (input) {
                case Commands.START:
                    chatStateService.updateChatStep(0, chatId);
                    ChatUser chatUser = chatUserService.createOrReturnChatUser(chatId);
                    if (chatUser.getYaToken() == null) {
                        message = chatStateService.updateStepAndGetMessage(ChatStates.TOKEN_STEP, chatId);
                        message.setText(message.getText() + "\n" + "Перейдите по ссылке " +
                            yandexMetricService.getTokenLink()

                        );
                    }
                    break;
                case Commands.UPDATE_TOKEN:
                    message = chatStateService.updateStepAndGetMessage(ChatStates.TOKEN_STEP, chatId);
                    message.setText(message.getText() + "\n" + "Перейдите по ссылке " +
                                        yandexMetricService.getTokenLink()

                    );
                    break;
                case Commands.ADD_METRIC:
                    chatStateService.updateChatStep(0, chatId);
                    message = metricService.initConversation(update.getMessage().getChatId());
                    break;
                case Commands.METRIC_LIST:
                    chatStateService.updateChatStep(0, chatId);
                    message = metricService.getMetricList(chatId, Commands.GET_METRIC);
                    break;
                case Commands.DELETE_METRIC:
                    chatStateService.updateChatStep(0, chatId);
                    message = metricService.getMetricList(chatId, Commands.DELETE_ONE_METRIC);
                    break;
                case Commands.EDIT_METRIC:
                    chatStateService.updateChatStep(0, chatId);
                    message = metricService.getMetricList(chatId, Commands.EDIT_ONE_METRIC);
                    break;
                case Commands.DEALS:
                    chatStateService.updateChatStep(0, chatId);
                    message = metricService.getMetricList(chatId, Commands.DEALS_EDIT);
                    break;
                default:
                    int chatStep = chatStateService.getCurrentStep(chatId);
                    switch (chatStep) {
                        case 0: message = getDefaultMessage(chatId);
                            break;
                        case ChatStates.TOKEN_STEP:
                            message = yandexMetricService.handleYandexToken(chatId,
                                                                            update.getMessage().getText());
                            break;
                        default:
                             message = metricService.handleInput(update.getMessage().getText(), chatId);
                            break;
                    }
                    break;
            }

            try {
                sendMessage(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return applicationProperties.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return applicationProperties.getBotToken();
    }



    public SendMessage getDefaultMessage(Long chatId) {
        SendMessage sendMessage = new SendMessage().
            setChatId(chatId)
            .setText("Введите команду");
        return sendMessage;
    }

}

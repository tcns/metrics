package ru.cedra.metrics.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import ru.cedra.metrics.service.bot.MetricBot;

import javax.annotation.PostConstruct;


/**
 * Created by tignatchenko on 22/04/17.
 */
@Configuration
@EnableAspectJAutoProxy (proxyTargetClass = true)
public class BotConfiguration {
    @Autowired
    private MetricBot metricBot;

    @PostConstruct
    public void init() throws TelegramApiRequestException {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        botsApi.registerBot(metricBot);
    }

}

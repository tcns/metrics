package ru.cedra.metrics.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import ru.cedra.metrics.domain.Metric;
import ru.cedra.metrics.service.bot.MetricBot;
import ru.cedra.metrics.service.bot.MetricService;
import ru.cedra.metrics.service.metric.CommonStatsService;
import ru.cedra.metrics.service.metric.YandexMetricService;
import ru.cedra.metrics.service.util.TaskUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


/**
 * Created by tignatchenko on 26/04/17.
 */
@Configuration

@EnableScheduling
public class SchedulerConfiguration implements SchedulingConfigurer {

    @Autowired
    MetricService metricService;

    @Autowired
    MetricBot metricBot;

    @Autowired
    CommonStatsService commonStatsService;

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        List<Metric> metrics = metricService.findAll();
        ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
        TaskScheduler taskScheduler = new ConcurrentTaskScheduler(localExecutor);
        taskRegistrar.setTaskScheduler(taskScheduler);
        for (Metric metric: metrics) {
            TaskUtil.registerReportTask(metricBot, taskRegistrar.getScheduler(), metric, commonStatsService);
            TaskUtil.registerFinalUpdateTask(metricBot, taskRegistrar.getScheduler(), metric, commonStatsService);
        }

        taskRegistrar.addCronTask(() -> {
            commonStatsService.deleteOldStats(61);
        }, "0 0 12 1 * ?");
    }
}

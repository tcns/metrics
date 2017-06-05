package ru.cedra.metrics.service.util;

import com.sun.javafx.collections.MappingChange;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import ru.cedra.metrics.domain.Metric;
import ru.cedra.metrics.service.bot.MetricBot;
import ru.cedra.metrics.service.metric.CommonStatsService;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;


/**
 * Created by tignatchenko on 27/04/17.
 */
public class TaskUtil {

    public static Map<Long, ScheduledFuture> reports = new HashMap<>();
    public static Map<Long, ScheduledFuture> asks = new HashMap<>();

    public static void deleteTask(TaskScheduler taskScheduler, Long metricId) {
        if (reports.get(metricId) != null) {
            reports.get(metricId).cancel(true);
        }
        if (asks.get(metricId) != null) {
            asks.get(metricId).cancel(true);
        }

    }
    public static void registerTask(CommonStatsService  commonStatsService,
                                    MetricBot metricBot, TaskScheduler taskScheduler, Metric metric) {
        if (metric.getReportTime() != null) {
            try {
                Integer.parseInt(metric.getReportTime());
                reports.put(metric.getId(), taskScheduler.schedule(
                    getRunnableReport(metricBot, metric, commonStatsService),
                    getTrigger(Integer.parseInt(metric.getReportTime()))
                ));
            } catch (Exception e) {
            }
        }

        if (metric.getAskTime() != null) {
            try {
                Integer.parseInt(metric.getAskTime());
                asks.put(metric.getId(), taskScheduler.schedule(
                    getRunnableQuestion(metricBot, metric),
                    getTrigger(Integer.parseInt(metric.getAskTime()))
                ));
            } catch (Exception e) {
            }
        }

    }

    private static Trigger getTrigger(Integer reportTime) {
        return triggerContext -> {
            Calendar nextExecutionTime =  new GregorianCalendar();
            //nextExecutionTime.setTime(new Date());
            //nextExecutionTime.set(Calendar.HOUR_OF_DAY, reportTime);
            //nextExecutionTime.set(Calendar.MINUTE, 0);
            //nextExecutionTime.add(Calendar.DAY_OF_YEAR, 1);
            nextExecutionTime.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
            nextExecutionTime.add(Calendar.MINUTE, 20);
            return nextExecutionTime.getTime();
        };
    }

    private static Runnable getRunnableReport(MetricBot metricBot, Metric metric, CommonStatsService commonStatsService) {
        return () -> {
            String report = commonStatsService.getReport(metric.getId());

            SendMessage sendMessage = new SendMessage()
                .setChatId(metric.getChatUser().getTelegramChatId())
                .setText(report);
            metricBot.sendMessageExternal(sendMessage);
        };
    }

    private static Runnable getRunnableQuestion(MetricBot metricBot, Metric metric) {
        return () -> {
            SendMessage sendMessage = new SendMessage()
                .setChatId(metric.getChatUser().getTelegramChatId())
                .setText("Напоминание: пожалуйста, введите количество звонков и сделок " +
                             "для метрики " + metric.getName() + " " +
                             "командой /deals");
            metricBot.sendMessageExternal(sendMessage);
        };
    }
}

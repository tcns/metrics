package ru.cedra.metrics.service.util;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import ru.cedra.metrics.domain.Commands;
import ru.cedra.metrics.domain.Metric;
import ru.cedra.metrics.service.bot.MetricBot;
import ru.cedra.metrics.service.metric.CommonStatsService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;


/**
 * Created by tignatchenko on 27/04/17.
 */
public class TaskUtil {

    public static Map<Long, ScheduledFuture> reports = new HashMap<>();

    public static void deleteTask(Long metricId) {
        if (reports.get(metricId) != null) {
            reports.get(metricId).cancel(true);
            reports.remove(metricId);
        }

    }
    public static void registerTask(MetricBot metricBot, TaskScheduler taskScheduler, Metric metric,
                                    CommonStatsService commonStatsService) {
        if (metric.getReportTime() != null) {
            try {
                Integer.parseInt(metric.getReportTime());
                reports.put(metric.getId(), taskScheduler.schedule(
                    getRunnableQuestion(metricBot, metric, commonStatsService),
                    getTrigger(Integer.parseInt(metric.getReportTime()))
                ));
            } catch (Exception e) {
            }
        }

    }

    private static Trigger getTrigger(Integer reportTime) {
        return triggerContext -> {
            Calendar nextExecutionTime =  new GregorianCalendar();
            nextExecutionTime.setTime(new Date());
            nextExecutionTime.set(Calendar.HOUR_OF_DAY, reportTime);
            nextExecutionTime.set(Calendar.MINUTE, 0);
            nextExecutionTime.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
            if (reportTime < LocalDateTime.now(ZoneId.of("+03:00")).getHour() + 1) {
                nextExecutionTime.add(Calendar.DAY_OF_YEAR, 1);
            }
           // nextExecutionTime.add(Calendar.SECOND, 30);
            return nextExecutionTime.getTime();
        };
    }

    private static Runnable getRunnableQuestion(MetricBot metricBot, Metric metric,
                                                CommonStatsService commonStatsService) {
        return () -> {
            SendMessage sendMessage = new SendMessage()
                .setChatId(metric.getChatUser().getTelegramChatId())
                .setText("Напоминание: пожалуйста, введите количество звонков и сделок " +
                             "для метрики " + metric.getName() + " командой " + Commands.DEALS);
            commonStatsService.getAndSaveStats(metric.getId(), LocalDate.now(ZoneId.of("+03:00")), true);
            metricBot.sendMessageExternal(sendMessage);
        };
    }
}

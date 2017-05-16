package ru.cedra.metrics.service.metric;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cedra.metrics.domain.CommonStats;
import ru.cedra.metrics.domain.Metric;
import ru.cedra.metrics.repository.CommonStatsRepository;
import ru.cedra.metrics.repository.MetricRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * Created by tignatchenko on 04/05/17.
 */
@Service
public class CommonStatsService {

    @Autowired
    MetricRepository metricRepository;
    @Autowired
    CommonStatsRepository commonStatsRepository;

    @Autowired
    YandexMetricService yandexMetricService;

    public CommonStats getAndSaveStats(Long metricId, boolean updateRemote) {
        CommonStats commonStats = new CommonStats();
        CommonStats dbStats = commonStatsRepository.findOneByDateAndMetric_Id(java.sql.Date.valueOf(LocalDate.now()), metricId);
        Metric metric = metricRepository.findOne(metricId);
        if (dbStats != null) {
            commonStats = dbStats;
        } else {
            commonStats.setDeals(0);
            commonStats.setCalls(0);
            commonStats.setClickCost(0.0f);
            commonStats.setBudget(0.0f);
            commonStats.setDate(java.sql.Date.valueOf(LocalDate.now()));
            commonStats.setMetric(metric);
        }
        if (updateRemote) {
            yandexMetricService.fillStats(metric.getChatUser().getTelegramChatId(), commonStats);
            commonStats = commonStatsRepository.save(commonStats);
        }

        return commonStats;
    }

    public void updateTodayCallsAndDeals(Integer calls, Integer deals, Long metricId) {
        Metric metric = metricRepository.findOne(metricId);
        CommonStats todayStats = getAndSaveStats(metricId, false);
        todayStats.setCalls(calls);
        todayStats.setDeals(deals);
        commonStatsRepository.save(todayStats);
    }

    public String getReport (Long metricId) {
        Metric metric = metricRepository.findOne(metricId);

        LocalDate today = LocalDate.now();
        LocalDate metricFromDate = metric.getReportFromDate().toLocalDate();
        long daysDiff = Math.min(30, Math.abs(ChronoUnit.DAYS.between(today, metricFromDate)));

        LocalDate from = today.minusDays(daysDiff);


        CommonStats todayStats = getAndSaveStats(metricId, true);

        Set<CommonStats> monthStats = commonStatsRepository
            .findByDateBetweenAndMetric_Id(java.sql.Date.valueOf(from),
                                           java.sql.Date.valueOf(today),
                                           metricId);

        StringBuilder sb = new StringBuilder();
        int dayGoalVisits = (int)Math.ceil((float)metric.getMonthCount() / 30);
        int dayGoalDeals = (int)Math.ceil((float)metric.getMonthDeals() / 30);

        int periodVisits = monthStats.stream().mapToInt(CommonStats::getVisits).sum();
        int periodDeals = monthStats.stream().mapToInt(CommonStats::getDeals).sum();

        sb.append(String.format("Цель за месяц: %d посещений, %d сделок " +
                                    "при средней цене клика %.1f",
                                metric.getMonthCount(), metric.getMonthDeals(), metric.getClickPrice()))
          .append("\n")
          .append("Показатели сегодня:\n")
          .append(String.format("Обращений: %d (звонков %d, заявок %d)",
                                todayStats.getCalls() + todayStats.getGoalAchievements(),
                                todayStats.getCalls(),
                                todayStats.getGoalAchievements()))
          .append("\n")
          .append(String.format("Конверсия сайта реальная / введенная: %,.2f%% / %,.2f%%",
                                todayStats.getVisits() == 0 ? 0f :
                                ((float)todayStats.getGoalAchievements() /
                                    todayStats.getVisits() * 100),
                                metric.getSiteConversion()))
          .append("\n")
          .append(String.format("Конверсия отдела продаж  %,.2f%%",
                                todayStats.getCalls() == 0 ? 0f :
                                ((float)todayStats.getDeals() /
                                    todayStats.getCalls() * 100)))
          .append("\n")
          .append(String.format("Расход бюджета %,.2f р.", todayStats.getBudget()))
          .append("\n")
          .append(String.format("Средняя цена клика реальная / введенная %,.2f р. / %,.2f р.",
                                todayStats.getClickCost(), metric.getClickPrice()))
          .append("\n")
          .append(String.format(
              "Cоответствие дневному плану посещений сайта: %,.2f%%\n",
              dayGoalVisits == 0 ? 100f :
              (float)todayStats.getVisits() / dayGoalVisits * 100))
          .append(String.format("\t Посещений сегодня %d из %d\n", todayStats.getVisits(),
                                dayGoalVisits))
          .append(String.format(
              "Cоответствие месячному плану посещений сайта: %,.2f%%\n",
              metric.getMonthCount() == 0 ? 100f :
              (float)periodVisits / metric.getMonthCount() * 100))
          .append(String.format(
              "Cоответствие дневному плану сделок: %,.2f%%\n",
              dayGoalDeals == 0 ? 100f :
              (float)todayStats.getDeals() / dayGoalDeals * 100))
          .append(String.format("\t Сделок сегодня %d из %d\n", todayStats.getDeals(),
                                dayGoalDeals))
          .append(String.format(
              "Cоответствие месячному плану сделок: %,.2f%%\n",
              metric.getMonthDeals() == 0 ? 100f :
              (float)periodDeals / metric.getMonthDeals() * 100));

        return sb.toString();



    }
}

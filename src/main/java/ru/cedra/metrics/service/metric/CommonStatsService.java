package ru.cedra.metrics.service.metric;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons
    .InlineKeyboardButton;
import ru.cedra.metrics.config.Constants;
import ru.cedra.metrics.domain.Commands;
import ru.cedra.metrics.domain.CommonStats;
import ru.cedra.metrics.domain.Emojis;
import ru.cedra.metrics.domain.Metric;
import ru.cedra.metrics.repository.CommonStatsRepository;
import ru.cedra.metrics.repository.MetricRepository;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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



    public CommonStats getAndSaveStats(Long metricId, LocalDate date, boolean updateRemote) {
        CommonStats commonStats = new CommonStats();

        CommonStats dbStats = commonStatsRepository.findOneByDateAndMetric_Id(java.sql.Date.valueOf(date), metricId);
        Metric metric = metricRepository.findOne(metricId);
        if (dbStats != null) {
            commonStats = dbStats;
        } else {
            commonStats.setDeals(0);
            commonStats.setDealsSet(false);
            commonStats.setCalls(0);
            commonStats.setGoalAchievements(0);
            commonStats.setClickCost(0.0f);
            commonStats.setIncome(0.0f);
            commonStats.setBudget(0.0f);
            commonStats.setDate(Timestamp.valueOf(LocalDate.now().atStartOfDay()));
            commonStats.setMetric(metric);
        }
        if (updateRemote) {
            yandexMetricService.fillStats(metric.getChatUser().getTelegramChatId(), commonStats);
            commonStats = commonStatsRepository.save(commonStats);
        }

        return commonStats;
    }

    public CommonStats getDayStats (Long metricId, Date date) {
        return commonStatsRepository.findOneByDateAndMetric_Id(date, metricId);
    }

    public void deleteOldStats (int days) {

        LocalDate localDate = LocalDate.now();
        localDate = localDate.minus(days, ChronoUnit.DAYS);
        commonStatsRepository.deleteByDateLessThan(java.sql.Date.valueOf(localDate));
    }

    public void updateCallsAndDeals(Integer calls, Integer deals, Float income, Long metricId,
                                    LocalDate date) {
        CommonStats todayStats = getAndSaveStats(metricId, date, false);
        todayStats.setCalls(calls);
        todayStats.setDeals(deals);
        todayStats.setDealsSet(true);
        todayStats.setIncome(income);
        commonStatsRepository.save(todayStats);
    }

    public SendMessage getDatesWithoutDeals (Long chatId, Long metricId) {
        Set<CommonStats> commonStats = commonStatsRepository.findByDealsSetAndMetric_Id(false, metricId);
        InlineKeyboardMarkup replyMarkup = new InlineKeyboardMarkup();
        final List<List<InlineKeyboardButton>> keyboardButtons = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        commonStats.parallelStream().map(CommonStats::getDate).forEach(a -> {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(dateFormat.format(a));
            button.setCallbackData(Commands.DEALS_DATE + Constants.DATE_F.format(a) + "_" + metricId);
            keyboardButtons.add(Collections.singletonList(button));
        });
        replyMarkup.setKeyboard(keyboardButtons);
        return new SendMessage().setText("Выберите дату, для которой не были введены сделки")
                                .setChatId(chatId)
                                .setReplyMarkup(replyMarkup);
    }
    static String f (String string, Object... args) {
        return String.format(string, args);
    }

    public String getReport (Long metricId) {
        Metric metric = metricRepository.findOne(metricId);

        LocalDate today = LocalDate.now(ZoneId.of("+03:00"));
        LocalDate metricFromDate = metric.getReportFromDate().toInstant().atZone(
            ZoneId.systemDefault()).toLocalDate();
        long daysDiff = Math.min(30, Math.abs(ChronoUnit.DAYS.between(today, metricFromDate)));

        LocalDate from = today.minusDays(daysDiff);


        CommonStats todayStats = getDayStats(metricId, java.sql.Date.valueOf(
            today));
        if (todayStats == null) {
            todayStats = getAndSaveStats(metricId, today, true);
        }

        Set<CommonStats> monthStats = commonStatsRepository
            .findByDateBetweenAndMetric_Id(java.sql.Date.valueOf(from),
                                           java.sql.Date.valueOf(today),
                                           metricId);

        StringBuilder sb = new StringBuilder();
        int dayGoalVisits = (int)Math.ceil((float)metric.getMonthCount() / 30);
        int dayGoalDeals = (int)Math.ceil((float)metric.getMonthDeals() / 30);

        int periodVisits = monthStats.stream().mapToInt(a -> a.getVisits() == null ? 0 : a.getVisits()).sum();
        double periodBudget = monthStats.stream().mapToDouble(a -> a.getBudget() == null ? 0 : a.getBudget()).sum();
        double periodIncome = monthStats.stream().mapToDouble(a -> a.getIncome() == null ? 0 : a.getIncome()).sum();

        float realConversion = todayStats.getVisits() == 0 ? 0f :
                               ((float)todayStats.getGoalAchievements() /
                                   todayStats.getVisits());
        float visitsGoalPercent = dayGoalVisits == 0 ? 100f :
                                  (float)todayStats.getVisits() / dayGoalVisits * 100;
        sb.append(f("Цель за месяц: прибыль %d р.\n", metric.getClearIncome()))
          .append("-----------------\n")
          .append("Введенные показатели:\n")
          .append(f("\tСредний чек - %.1f р.\n", metric.getAvgCheck()))
          .append(f("\tРентабельность - %.1f%% р.\n", metric.getRent() * 100))
          .append(f("\tКонверсия отдела продаж - %.1f%% р.\n", metric.getSaleConversion() * 100))
          .append(f("\tКонверсия сайта - %.1f%% р.\n", metric.getSiteConversion() * 100))
          .append("-----------------\n")
          .append("Гипотеза:\n")
          .append(f("\tСделать %d посещений на сайте.\n", metric.getMonthCount()))
          .append(f("\tСр. цена клика %.1f р.\n", metric.getClickPrice()))
          .append(f("\tЗаключить %d сделок.\n", metric.getMonthDeals()))
          .append("-----------------\n")
          .append("Показатели сегодня:\n")
          .append(f("\tОбращений: %d (звонков %d, заявок %d)\n",
                                todayStats.getCalls() + todayStats.getGoalAchievements(),
                                todayStats.getCalls(),
                                todayStats.getGoalAchievements()))
          .append(f("\tКонверсия сайта реальная / введенная: %,.2f%% / %,.2f%% ",
                                realConversion * 100,
                                metric.getSiteConversion() * 100))
          .append(realConversion >= metric.getSiteConversion() ? Emojis.CHECK : Emojis.CROSS).append("\n")
          .append(f("\tКонверсия отдела продаж  %,.2f%%\n",
                                todayStats.getCalls() == 0 ? 0f :
                                ((float)todayStats.getDeals() /
                                    todayStats.getCalls() * 100)))
          .append(f("\tРасход бюджета за день %,.2f р.\n", todayStats.getBudget()))
          .append(f("\tСредняя цена клика реальная / введенная %,.2f р. / %,.2f р.",
                                todayStats.getClickCost(), metric.getClickPrice()))
          .append(f("\tCоответствие дневному плану посещений сайта: %,.2f%%\n", visitsGoalPercent))
          .append(String.format("\t Посещений сегодня %d из %d ", todayStats.getVisits(),
                                dayGoalVisits))
          .append(visitsGoalPercent >= 100f ? Emojis.CHECK : Emojis.CROSS).append("\n")
          .append("-----------------\n")
          .append(f("\tРасход бюджета за период %,.2f р.\n", periodBudget))
          .append(f("\tCоответствие месячному плану посещений сайта: %,.2f%%\n",
              metric.getMonthCount() == 0 ? 100f :
              (float)periodVisits / metric.getMonthCount() * 100))
          .append(f("\tСделок сегодня %d из %d на сумму %,.2f\n", todayStats.getDeals(),
                                dayGoalDeals, todayStats.getIncome() == null ? 0f : todayStats.getIncome()))
          .append(f("\tЧистая прибыль с вычетом рекламного бюджета %,.2f\n", periodIncome - periodBudget))
            .append(f("\tROMI %,.2f%%\n", periodIncome / (periodBudget == 0 ? 1 : periodBudget) * 100));

        return sb.toString();
    }
}

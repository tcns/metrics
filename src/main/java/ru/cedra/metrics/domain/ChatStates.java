package ru.cedra.metrics.domain;

import com.google.common.collect.Lists;
import io.swagger.models.auth.In;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by tignatchenko on 23/04/17.
 */
public class ChatStates {
    public static Map<Integer, String> states = new HashMap<>();



    public static final int COUNT_STEP = 1;
    public static final int NAME_STEP = 2;
    public static final int GOAL_STEP = 3;
    public static final int INCOME_STEP = 4;
    public static final int RENT_STEP = 5;
    public static final int SALE_CONVERSION_STEP = 6;
    public static final int AVG_CHECK_STEP = 7;
    public static final int SITE_CONVERSION_STEP = 8;
    public static final int CLICK_PRICE_STEP = 9;
    public static final int REPORT_TIME_STEP = 10;
    public static final int CAMPAIGNS_IDS = 11;
    public static final int METRIC_COMPLETE = 12;
    public static final int TOKEN_STEP = 13;
    public static final int DEALS_EDIT = 14;

    public static List<Integer> paramStates = Lists.newArrayList(NAME_STEP,
                                                                 GOAL_STEP,
                                                                 INCOME_STEP,
                                                                 RENT_STEP,
                                                                 SALE_CONVERSION_STEP,
                                                                 AVG_CHECK_STEP,
                                                                 SITE_CONVERSION_STEP,
                                                                 CLICK_PRICE_STEP,
                                                                 CAMPAIGNS_IDS);

    static {
        states.put(COUNT_STEP, "Выберете счетчик");
        states.put(NAME_STEP, "Введите название");
        states.put(GOAL_STEP, "Введите идентификатор цели");
        states.put(INCOME_STEP, "Сколько денег ты хотел бы получать чистыми (месяц)?");
        states.put(RENT_STEP, "Рентабельность бизнеса (%)");
        states.put(SALE_CONVERSION_STEP, "Конверсия отдела продаж (%)");
        states.put(AVG_CHECK_STEP, "Средний чек");
        states.put(SITE_CONVERSION_STEP, "Предполагаемая конверсия сайта(%)");
        states.put(CLICK_PRICE_STEP, "Предполагаемая цена за клик");
        states.put(REPORT_TIME_STEP, "В какое время выводить показатели - укажите московское время в часах (н-р '22')");
        states.put(METRIC_COMPLETE, "Метрика сохранена!");
        states.put(TOKEN_STEP, "Введите код подтверждения яндекс метрики");
        states.put(DEALS_EDIT, "Введите через пробел количество звонков, сделок и сумму сделок");
        states.put(CAMPAIGNS_IDS, "Ввдеите идентификаторы кампаний в яндекс директе через пробел");
    }
}

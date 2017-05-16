package ru.cedra.metrics.service.bot;

import org.springframework.stereotype.Service;
import ru.cedra.metrics.domain.Metric;


/**
 * Created by tignatchenko on 04/05/17.
 */
@Service
public class CalcService {

    public int calcMonthDeals(Metric metric) {
        Integer deals = (int)Math.ceil(metric.getClearIncome() / (metric.getAvgCheck() * metric.getRent()));
        return deals;
    }

    public int calcMonthVisits(int monthDeals, float saleConversion, float siteConversion) {
        Integer visits = (int)Math.ceil(monthDeals / saleConversion / siteConversion);
        return visits;
    }
}

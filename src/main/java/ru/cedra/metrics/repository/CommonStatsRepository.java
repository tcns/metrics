package ru.cedra.metrics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cedra.metrics.domain.CommonStats;

import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * Created by tignatchenko on 04/05/17.
 */
public interface CommonStatsRepository extends JpaRepository<CommonStats, Long> {
    Set<CommonStats> findByDateBetweenAndMetric_Id (Date first, Date last, Long metricId);

    Set<CommonStats> findByDealsSetAndMetric_Id (Boolean dealsSet, Long metricId);

    CommonStats findOneByDateAndMetric_Id (Date date, Long metricId);

    void deleteByDateLessThan(Date date);
}

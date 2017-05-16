package ru.cedra.metrics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cedra.metrics.domain.CommonStats;

import java.util.Date;
import java.util.Set;


/**
 * Created by tignatchenko on 04/05/17.
 */
public interface CommonStatsRepository extends JpaRepository<CommonStats, Long> {
    Set<CommonStats> findByDateBetweenAndMetric_Id (Date first, Date last, Long metricId);

    CommonStats findOneByDateAndMetric_Id (Date date, Long metricId);
}

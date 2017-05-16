package ru.cedra.metrics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cedra.metrics.domain.Metric;

import java.util.Set;


/**
 * Created by tignatchenko on 24/04/17.
 */
public interface MetricRepository extends JpaRepository<Metric, Long> {
    Set<Metric> findByChatUser_TelegramChatId (Long chatId);
}

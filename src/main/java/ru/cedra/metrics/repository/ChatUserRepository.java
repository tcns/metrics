package ru.cedra.metrics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cedra.metrics.domain.ChatUser;


/**
 * Created by tignatchenko on 25/04/17.
 */
public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {
    ChatUser getChatUserByTelegramUsername (String telegranUsername);
    ChatUser getChatUserByTelegramChatId (Long telegramChatId);
}

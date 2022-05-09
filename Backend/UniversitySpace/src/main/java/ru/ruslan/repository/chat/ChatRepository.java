package ru.ruslan.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ruslan.entity.chat.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Chat getByChatId(Long chatId);
    Chat findByName(String name);
}

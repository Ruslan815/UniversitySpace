package ru.ruslan.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ruslan.entity.chat.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
    Chat getByChatId(Integer chatId);
    Chat findByName(String name);
}

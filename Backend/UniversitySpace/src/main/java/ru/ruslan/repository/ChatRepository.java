package ru.ruslan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ruslan.entity.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
    Chat getByChatId(Integer chatId);
    Chat findByName(String name);
}

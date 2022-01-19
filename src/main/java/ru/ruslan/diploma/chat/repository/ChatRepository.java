package ru.ruslan.diploma.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ruslan.diploma.chat.model.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
}

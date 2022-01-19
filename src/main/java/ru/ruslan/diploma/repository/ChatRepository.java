package ru.ruslan.diploma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ruslan.diploma.model.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
}

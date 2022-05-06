package ru.ruslan.repository.chat;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ruslan.entity.chat.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Message> findAllByChatId(Integer chatId, Sort sort);
}

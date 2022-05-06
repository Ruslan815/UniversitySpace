package ru.ruslan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ruslan.entity.chat.Message;
import ru.ruslan.entity.chat.MessageView;
import ru.ruslan.entity.user.User;
import ru.ruslan.repository.chat.MessageRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private final MessageRepository messageRepository;
    @Autowired
    private final DatabaseService databaseService;

    public MessageService(MessageRepository messageRepository, DatabaseService databaseService) {
        this.messageRepository = messageRepository;
        this.databaseService = databaseService;
    }

    public MessageView create(Message someMessage) {
        return new MessageView(messageRepository.save(someMessage));
    }

    public List<MessageView> getAllByChatId(Integer chatId) {
        List<Message> tempList = messageRepository.findAllByChatId(chatId, Sort.by(Sort.Direction.DESC, "sendTime"));
        List<MessageView> responseList = new ArrayList<>();
        for (Message message : tempList) {
            responseList.add(new MessageView(message));
        }

        return responseList;
    }

    public synchronized List<MessageView> readMessages(User someUser, Integer chatId) {
        return databaseService.readMessages(someUser, chatId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public synchronized boolean isUnreadMessagesExist(User someUser, Integer chatId) {
        List<Message> chatMessagesList = messageRepository.findAllByChatId(chatId, Sort.by(Sort.Direction.DESC, "sendTime"));
        for (Message someMessage : chatMessagesList) {
            if (someMessage.getUsersWhoDidNotRead().contains(someUser)) {
                return true;
            }
        }

        return false;
    }
}

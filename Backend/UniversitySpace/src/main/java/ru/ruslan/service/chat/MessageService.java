package ru.ruslan.service.chat;

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
import ru.ruslan.service.user.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private final MessageRepository messageRepository;
    @Autowired
    private final DatabaseService databaseService;
    @Autowired
    private final UserService userService;

    public MessageService(MessageRepository messageRepository, DatabaseService databaseService, UserService userService) {
        this.messageRepository = messageRepository;
        this.databaseService = databaseService;
        this.userService = userService;
    }

    public MessageView create(Message someMessage) {
        return new MessageView(messageRepository.save(someMessage));
    }

    public List<MessageView> getAllByChatId(Long chatId, Long userId) {
        User user = userService.findUserById(userId);
        List<Message> tempList = messageRepository.findAllByChatId(chatId, Sort.by(Sort.Direction.DESC, "sendTime"));
        List<MessageView> responseList = new ArrayList<>();
        for (Message message : tempList) {
            message.getUsersWhoDidNotRead().remove(user);
            MessageView messageView = new MessageView(message);
            String senderUsername = userService.findUserById(messageView.getUserId()).getUsername();
            messageView.setUsername(senderUsername);
            responseList.add(messageView);
        }
        messageRepository.saveAll(tempList); // update users who did not read

        return responseList;
    }

    public synchronized List<MessageView> readMessages(User someUser, Long chatId) {
        List<MessageView> answerList = databaseService.readMessages(someUser, chatId);
        for (MessageView messageView : answerList) {
            String senderUsername = userService.findUserById(messageView.getUserId()).getUsername();
            messageView.setUsername(senderUsername);
        }
        return answerList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public synchronized boolean isUnreadMessagesExist(User someUser, Long chatId) {
        List<Message> chatMessagesList = messageRepository.findAllByChatId(chatId, Sort.by(Sort.Direction.DESC, "sendTime"));
        for (Message someMessage : chatMessagesList) {
            if (someMessage.getUsersWhoDidNotRead().contains(someUser)) {
                return true;
            }
        }

        return false;
    }
}

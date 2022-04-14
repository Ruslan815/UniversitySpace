package ru.ruslan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ruslan.entity.Message;
import ru.ruslan.entity.MessageView;
import ru.ruslan.entity.User;
import ru.ruslan.repository.MessageRepository;
import ru.ruslan.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {

    @Autowired
    private final MessageRepository messageRepository;
    @Autowired
    private final UserRepository userRepository;

    // ChatId, UsersWhoDidNotRead
    private final Map<Integer, List<Message>> unreadMessages = new HashMap<>();

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public MessageView create(Message someMessage) {
        addUnreadMessage(someMessage);
        return new MessageView(messageRepository.save(someMessage));
    }

    public void addUnreadMessage(Message someMessage) {
        Integer chatId = someMessage.getChatId();
        if (unreadMessages.containsKey(chatId)) {
            unreadMessages.get(chatId).add(someMessage);
        } else {
            List<Message> newList = new ArrayList<>();
            newList.add(someMessage);
            unreadMessages.put(chatId, newList);
        }
    }

    public List<MessageView> getAllByChatId(Integer chatId) {
        List<Message> tempList = messageRepository.findAllByChatId(chatId, Sort.by(Sort.Direction.DESC, "sendTime"));
        List<MessageView> responseList = new ArrayList<>();
        long currentTimeSec = System.currentTimeMillis() / 1000;
        for (Message message : tempList) {
            if (message.getSendTimeSec() <= currentTimeSec) {
                responseList.add(new MessageView(message));
            }
        }

        return responseList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public synchronized List<MessageView> readMessages(User someUser, Integer chatId) {
        //List<Message> chatMessagesList = messageRepository.findAllByChatId(chatId, Sort.by(Sort.Direction.DESC, "sendTime"));
        List<Message> chatMessagesList = unreadMessages.get(chatId);

        System.out.println(Thread.currentThread().getId() + ") START LIST");
        for (Message someMessage : chatMessagesList) {
            System.out.print(Thread.currentThread().getId() + ") " + someMessage.getMessageId() + " : ");
            for (User tempUser : someMessage.getUsersWhoDidNotRead()) {
                System.out.print(tempUser.getId() + ", ");
            }
            System.out.println();
        }

        List<MessageView> responseList = new ArrayList<>();
        for (Message someMessage : chatMessagesList) {
            if (someMessage.getUsersWhoDidNotRead().contains(someUser)) {
                someMessage.getUsersWhoDidNotRead().remove(someUser);
                messageRepository.saveAndFlush(someMessage);
                responseList.add(new MessageView(someMessage));
            }
        }
        unreadMessages.put(chatId, chatMessagesList);

        System.out.println(Thread.currentThread().getId() + ") END LIST");
        for (Message someMessage : unreadMessages.get(chatId)) {
            System.out.print(Thread.currentThread().getId() + ") " + someMessage.getMessageId() + " : ");
            for (User tempUser : someMessage.getUsersWhoDidNotRead()) {
                System.out.print(tempUser.getId() + ", ");
            }
            System.out.println();
        }

        System.out.println(Thread.currentThread().getId() + ") FETCHED LIST");
        List<Message> fetchedMessages = unreadMessages.get(chatId);
        for (Message someMessage : fetchedMessages) {
            System.out.print(Thread.currentThread().getId() + ") " + someMessage.getMessageId() + " : ");
            for (User tempUser : someMessage.getUsersWhoDidNotRead()) {
                System.out.print(tempUser.getId() + ", ");
            }
            System.out.println();
        }

        return responseList;
    }

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

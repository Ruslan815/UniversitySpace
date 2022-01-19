package ru.ruslan.diploma.chat.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.ruslan.diploma.chat.repository.MessageRepository;
import ru.ruslan.diploma.chat.model.Message;
import ru.ruslan.diploma.chat.model.MessageView;
import ru.ruslan.diploma.chat.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public MessageView create(Message someMessage) {
        return new MessageView(messageRepository.save(someMessage));
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

    public void deleteOldMessages() {
        List<Message> tempList = messageRepository.findAll();
        List<Integer> deletedMessagesIdsList = new ArrayList<>();
        long currentTimeSec = System.currentTimeMillis() / 1000;
        for (Message tempMessage : tempList) {
            long currentMessageLifeTime = currentTimeSec - tempMessage.getSendTimeSec(); // sendTimeSec is always not null
            Long messageLifeTimeSec = tempMessage.getLifetimeSec();
            if (messageLifeTimeSec != null && currentMessageLifeTime >= messageLifeTimeSec) { // If lifeTimeSec not specified
                deletedMessagesIdsList.add(tempMessage.getMessageId());
            }
        }
        if (!deletedMessagesIdsList.isEmpty()) {
            messageRepository.deleteAllById(deletedMessagesIdsList);
        }
    }

    public List<MessageView> readMessages(User someUser, Integer chatId) {
        List<Message> chatMessagesList = messageRepository.findAllByChatId(chatId, Sort.by(Sort.Direction.DESC, "sendTime"));
        List<MessageView> responseList = new ArrayList<>();
        for (Message someMessage : chatMessagesList) {
            if (someMessage.getUsersWhoDidNotRead().remove(someUser)) {
                responseList.add(new MessageView(someMessage));
            }
        }
        messageRepository.saveAll(chatMessagesList);

        return responseList;
    }
}

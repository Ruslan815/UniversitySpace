package ru.ruslan.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.ruslan.entity.Message;
import ru.ruslan.entity.MessageView;
import ru.ruslan.entity.User;
import ru.ruslan.repository.MessageRepository;

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
}

package ru.ruslan.diploma.chat.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import ru.ruslan.diploma.chat.model.Message;
import ru.ruslan.diploma.chat.model.MessageView;
import ru.ruslan.diploma.chat.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @MockBean
    private MessageRepository messageRepository;

    private final int chatId = 1;
    private final int messageId = 1;
    private final int userId = 1;
    private final String text = "someText";
    private final String time = "2021-07-12 13:45:00";

    @Test
    void createSuccessful() {
        Message passedMessage = new Message(null, userId, null, text, time);
        Message returnedMessage = new Message(messageId, userId, null, text, time);
        MessageView expectedMessageView = new MessageView(returnedMessage);
        Mockito.when(messageRepository.save(passedMessage)).thenReturn(returnedMessage);

        MessageView actualMessageView = messageService.create(passedMessage);

        assertEquals(expectedMessageView, actualMessageView);
    }

    @Test
    void createSuccessfulPrivateChatMessage() {
        Message passedMessage = new Message(null, userId, chatId, text, time);
        Message returnedMessage = new Message(messageId, userId, chatId, text, time);
        MessageView expectedMessageView = new MessageView(returnedMessage);
        Mockito.when(messageRepository.save(passedMessage)).thenReturn(returnedMessage);

        MessageView actualMessageView = messageService.create(passedMessage);

        assertEquals(expectedMessageView, actualMessageView);
    }

    @Test
    void getAllByChatIdSuccessful() {
        List<Message> list = new ArrayList<>();
        Message someMessage = new Message(messageId, userId, chatId, text, time);
        someMessage.setSendTimeSec(1L);
        list.add(someMessage);
        List<MessageView> expectedList = new ArrayList<>();
        expectedList.add(new MessageView(userId, text, time));
        Mockito.when(messageRepository.findAllByChatId(chatId, Sort.by(Sort.Direction.DESC, "sendTime"))).thenReturn(list);

        List<MessageView> actualList = messageService.getAllByChatId(chatId);

        assertEquals(expectedList, actualList);
    }
}

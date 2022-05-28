package ru.ruslan.controller.chat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ruslan.entity.chat.Chat;
import ru.ruslan.entity.chat.ChatView;
import ru.ruslan.entity.chat.Message;
import ru.ruslan.entity.chat.MessageView;
import ru.ruslan.service.chat.ChatService;
import ru.ruslan.service.chat.MessageService;
import ru.ruslan.service.user.UserService;

import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MessageControllerTest {

    @Autowired
    private MessageController messageController;

    @MockBean
    private MessageService messageService;

    @MockBean
    private UserService userService;

    @MockBean
    private ChatService chatService;

    private final Long userId = 1L;
    private final String userName = "username";
    private final Long chatId = 1L;
    private final String chatName = "chatName";
    private final String messageContent = "messageContent";

    @Test
    public void createMessageSuccessful() {
        Message message = new Message();
        message.setUserId(userId);
        message.setText(messageContent);
        message.setChatId(chatId);
        long currentTimeInMillis = System.currentTimeMillis();
        message.setSendTimeSec(currentTimeInMillis / 1000);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        message.setSendTime(formatter.format(currentTimeInMillis));

        MessageView messageView = new MessageView(message);
        Chat chat = new Chat();
        ResponseEntity<?> expectedResponse = ResponseEntity.ok(messageView);

        Mockito.when(chatService.getByChatId(chatId)).thenReturn(chat);
        Mockito.when(messageService.create(message)).thenReturn(messageView);

        ResponseEntity<?> actualResponse = messageController.create(message);

        assertEquals(expectedResponse, actualResponse);
    }
}
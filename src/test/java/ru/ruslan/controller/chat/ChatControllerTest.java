package ru.ruslan.controller.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import ru.ruslan.service.chat.ChatService;
import ru.ruslan.service.user.UserService;

import static org.junit.Assert.*;

@SpringBootTest // will bootstrap the full application context
@RunWith(SpringRunner.class) // provides support for loading ApplicationContext in JUnit 4
public class ChatControllerTest {

    @Autowired
    private ChatController chatController;

    @MockBean
    private ChatService chatService;

    @MockBean
    private UserService userService;

    private final Long userId = 1L;
    private final Long chatId = 1L;
    private final String name = "someName";
    // private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void create() {
        Chat passedChat = new Chat(chatId, name);
        ChatView expectedChat = new ChatView(passedChat);
        ResponseEntity<?> expectedResponse = ResponseEntity.ok(expectedChat);
        try {
            Mockito.when(chatService.create(passedChat)).thenReturn(expectedChat);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResponseEntity<?> actualResponse = chatController.create(passedChat);

        assertEquals(expectedResponse, actualResponse);
    }
}
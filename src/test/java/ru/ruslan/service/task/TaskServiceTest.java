package ru.ruslan.service.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ruslan.controller.chat.ChatController;
import ru.ruslan.entity.chat.ChatMember;
import ru.ruslan.entity.user.User;
import ru.ruslan.service.chat.ChatService;
import ru.ruslan.service.user.UserService;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TaskServiceTest {

    @Autowired
    private ChatController chatController;

    @MockBean
    private ChatService chatService;

    @MockBean
    private UserService userService;

    private final Long userId = 1L;
    private final String userName = "username";
    private final Long chatId = 1L;
    private final String chatName = "chatName";

    @Test
    public void enterChatSuccessful() {
        ChatMember chatMember = new ChatMember(userId, chatId);
        User user = new User();
        ResponseEntity<?> expectedResponse = ResponseEntity.ok().body(userName);
        Mockito.when(userService.findUserById(userId)).thenReturn(user);

        ResponseEntity<?> actualResponse = chatController.enterChat(chatMember);

        assertEquals(expectedResponse, actualResponse);
    }
}
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
import ru.ruslan.entity.chat.ChatMember;
import ru.ruslan.entity.chat.ChatView;
import ru.ruslan.entity.user.User;
import ru.ruslan.service.chat.ChatService;
import ru.ruslan.service.user.UserService;

import static org.junit.Assert.assertEquals;

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
    private final String userName = "username";
    private final Long chatId = 1L;
    private final String chatName = "chatName";

    @Test
    public void createChatSuccessful() {
        Chat passedChat = new Chat(chatId, chatName);
        ChatView expectedChat = new ChatView(passedChat);
        ResponseEntity<?> expectedResponse = ResponseEntity.ok().body(expectedChat);
        try {
            Mockito.when(chatService.create(passedChat)).thenReturn(expectedChat);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResponseEntity<?> actualResponse = chatController.create(passedChat);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void createChatFailedChatNameAlreadyExists() {
        Chat passedChat = new Chat(chatId, chatName);
        ResponseEntity<?> expectedResponse = ResponseEntity.status(500).body("CHAT NAME IS BUSY");
        Mockito.when(chatService.isChatNameExists(chatName)).thenReturn(true);

        ResponseEntity<?> actualResponse = chatController.create(passedChat);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void createChatFailedChatNameNotFound() {
        Chat passedChat = new Chat(chatId, chatName);
        ResponseEntity<?> expectedResponse = ResponseEntity.status(500).body("CHAT NAME NOT FOUND");
        try {
            Mockito.when(chatService.create(passedChat)).thenThrow(Exception.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResponseEntity<?> actualResponse = chatController.create(passedChat);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void enterChatSuccessful() {
        ChatMember chatMember = new ChatMember(userId, chatId);
        User user = new User();
        user.setUsername(userName);
        ResponseEntity<?> expectedResponse = ResponseEntity.ok().body(userName);
        Mockito.when(userService.findUserById(userId)).thenReturn(user);
        Mockito.when(chatService.enterChat(user, chatId)).thenReturn(true);
        Mockito.when(userService.findUserById(userId)).thenReturn(user);

        ResponseEntity<?> actualResponse = chatController.enterChat(chatMember);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void enterChatFailedUserAlreadyInChat() {
        ChatMember chatMember = new ChatMember(userId, chatId);
        User user = new User();
        ResponseEntity<?> expectedResponse = ResponseEntity.status(500).body("User already in chat!");
        Mockito.when(userService.findUserById(userId)).thenReturn(user);
        Mockito.when(chatService.enterChat(user, chatId)).thenReturn(false);

        ResponseEntity<?> actualResponse = chatController.enterChat(chatMember);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void leaveChatSuccessful() {
        ChatMember chatMember = new ChatMember(userId, chatId);
        User user = new User();
        user.setUsername(userName);
        ResponseEntity<?> expectedResponse = ResponseEntity.ok().body(userName);
        Mockito.when(userService.findUserById(userId)).thenReturn(user);
        Mockito.when(chatService.leaveChat(user, chatId)).thenReturn(true);
        Mockito.when(userService.findUserById(userId)).thenReturn(user);

        ResponseEntity<?> actualResponse = chatController.leaveChat(chatMember);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void leaveChatFailedUserNotInChat() {
        ChatMember chatMember = new ChatMember(userId, chatId);
        User user = new User();
        ResponseEntity<?> expectedResponse = ResponseEntity.status(500).body("User not in chat!");
        Mockito.when(userService.findUserById(userId)).thenReturn(user);
        Mockito.when(chatService.leaveChat(user, chatId)).thenReturn(false);

        ResponseEntity<?> actualResponse = chatController.leaveChat(chatMember);

        assertEquals(expectedResponse, actualResponse);
    }
}
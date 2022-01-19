package ru.ruslan.diploma.chat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import ru.ruslan.diploma.chat.error.ValidationResult;
import ru.ruslan.diploma.chat.model.Chat;
import ru.ruslan.diploma.chat.model.ChatMember;
import ru.ruslan.diploma.chat.model.ChatView;
import ru.ruslan.diploma.chat.service.ChatService;
import ru.ruslan.diploma.chat.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
class ChatControllerTest {

    @Autowired
    private ChatController chatController;

    @MockBean
    private ChatService chatService;

    @MockBean
    private UserService userService;

    private final Integer userId = 1;
    private final Integer chatId = 1;
    private final String name = "someName";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createSuccessfulPrivateChat() {
        Chat passedChat = new Chat(chatId, name);
        ChatView expectedChat = new ChatView(chatId, name);
        ResponseEntity<?> expectedResponse = ResponseEntity.ok(expectedChat);
        try {
            Mockito.when(chatService.create(passedChat)).thenReturn(expectedChat);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResponseEntity<?> actualResponse = chatController.create(passedChat);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void createFailedChatNameNotFound() {
        Chat passedChat = new Chat(chatId, name);
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.CHAT_NAME_NOT_FOUND);
        try {
            Mockito.when(chatService.create(passedChat)).thenThrow(Exception.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResponseEntity<?> actualResponse = chatController.create(passedChat);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void createFailedMalformedRssLink() {
        Chat passedChat = new Chat(chatId, name);
        passedChat.setRssLink("errorLink");
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.MALFORMED_RSS_LINK);
        try {
            Mockito.when(chatService.create(passedChat)).thenThrow(Exception.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResponseEntity<?> actualResponse = chatController.create(passedChat);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void createFailedUrlConnectionFailed() {
        Chat passedChat = new Chat(chatId, name);
        passedChat.setRssLink("https://somesite.su");
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.URL_CONNECTION_FAILED);
        try {
            Mockito.when(chatService.create(passedChat)).thenThrow(Exception.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResponseEntity<?> actualResponse = chatController.create(passedChat);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getSuccessful() {
        List<ChatView> expectedList = new ArrayList<>();
        expectedList.add(new ChatView(chatId, name));
        expectedList.add(new ChatView(chatId + 1, name));
        Mockito.when(chatService.getAll()).thenReturn(expectedList);

        List<ChatView> actualList = chatController.get();

        assertEquals(expectedList, actualList);
    }

    @Test
    void getSuccessfulEmptyList() {
        List<ChatView> expectedList = new ArrayList<>();
        Mockito.when(chatService.getAll()).thenReturn(expectedList);

        List<ChatView> actualList = chatController.get();

        assertEquals(expectedList, actualList);
    }

    @Test
    void enterChatSuccessful() {
        ChatMember chatMember = new ChatMember(userId, chatId);
        ResponseEntity<?> expectedResponse = null;
        try {
            JsonNode jsonResponse = objectMapper.readTree("{\"enterStatus\": \"You successfully entered the chat №" + chatId + "\"}");
            expectedResponse = ResponseEntity.ok(jsonResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Mockito.when(userService.isUserExist(userId)).thenReturn(true);
        Mockito.when(chatService.isPrivateChatExist(chatId)).thenReturn(true);
        Mockito.when(chatService.enterChat(Mockito.any(), eq(chatId))).thenReturn(true);

        ResponseEntity<?> actualResponse = chatController.enterChat(chatMember);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void enterChatFailedUserNotFound() {
        ChatMember chatMember = new ChatMember(userId, chatId);
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.USER_NOT_FOUND);
        Mockito.when(userService.isUserExist(userId)).thenReturn(false);

        ResponseEntity<?> actualResponse = chatController.enterChat(chatMember);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void enterChatFailedChatNotFound() {
        ChatMember chatMember = new ChatMember(userId, chatId);
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.CHAT_NOT_FOUND);
        Mockito.when(userService.isUserExist(userId)).thenReturn(true);
        Mockito.when(chatService.isPrivateChatExist(chatId)).thenReturn(false);

        ResponseEntity<?> actualResponse = chatController.enterChat(chatMember);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void enterChatFailedUserAlreadyInChat() {
        ChatMember chatMember = new ChatMember(userId, chatId);
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.USER_ALREADY_IN_CHAT);
        Mockito.when(userService.isUserExist(userId)).thenReturn(true);
        Mockito.when(chatService.isPrivateChatExist(chatId)).thenReturn(true);
        Mockito.when(chatService.enterChat(Mockito.any(), eq(chatId))).thenReturn(false);

        ResponseEntity<?> actualResponse = chatController.enterChat(chatMember);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void leaveChatSuccessful() {
        ChatMember chatMember = new ChatMember(userId, chatId);
        ResponseEntity<?> expectedResponse = null;
        try {
            JsonNode jsonResponse = objectMapper.readTree("{\"leaveStatus\": \"You successfully left the chat №" + chatId + "\"}");
            expectedResponse = ResponseEntity.ok(jsonResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Mockito.when(userService.isUserExist(userId)).thenReturn(true);
        Mockito.when(chatService.isPrivateChatExist(chatId)).thenReturn(true);
        Mockito.when(chatService.leaveChat(Mockito.any(), eq(chatId))).thenReturn(true);

        ResponseEntity<?> actualResponse = chatController.leaveChat(chatMember);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void leaveChatFailedUserNotFound() {
        ChatMember chatMember = new ChatMember(userId, chatId);
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.USER_NOT_FOUND);
        Mockito.when(userService.isUserExist(userId)).thenReturn(false);

        ResponseEntity<?> actualResponse = chatController.leaveChat(chatMember);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void leaveChatFailedChatNotFound() {
        ChatMember chatMember = new ChatMember(userId, chatId);
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.CHAT_NOT_FOUND);
        Mockito.when(userService.isUserExist(userId)).thenReturn(true);
        Mockito.when(chatService.isPrivateChatExist(chatId)).thenReturn(false);

        ResponseEntity<?> actualResponse = chatController.leaveChat(chatMember);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void leaveChatFailedUserNotInChat() {
        ChatMember chatMember = new ChatMember(userId, chatId);
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.USER_NOT_IN_CHAT);
        Mockito.when(userService.isUserExist(userId)).thenReturn(true);
        Mockito.when(chatService.isPrivateChatExist(chatId)).thenReturn(true);
        Mockito.when(chatService.enterChat(Mockito.any(), eq(chatId))).thenReturn(false);

        ResponseEntity<?> actualResponse = chatController.leaveChat(chatMember);

        assertEquals(expectedResponse, actualResponse);
    }
}

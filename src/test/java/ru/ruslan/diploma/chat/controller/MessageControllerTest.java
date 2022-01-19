package ru.ruslan.diploma.chat.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import ru.ruslan.diploma.chat.error.ValidationResult;
import ru.ruslan.diploma.chat.model.Chat;
import ru.ruslan.diploma.chat.model.Message;
import ru.ruslan.diploma.chat.model.MessageView;
import ru.ruslan.diploma.chat.service.ChatService;
import ru.ruslan.diploma.chat.service.MessageService;
import ru.ruslan.diploma.chat.service.UserService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class MessageControllerTest {

    @Autowired
    private MessageController messageController;

    @MockBean
    private MessageService messageService;

    @MockBean
    private UserService userService;

    @MockBean
    private ChatService chatService;

    private final Integer messageId = 1;
    private final Integer userId = 2;
    private final Integer chatId = 3;
    private final String messageText = "Ciao";
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    @Timeout(value = 1000, unit = MILLISECONDS)
    void createSuccessfulGeneralChatMessage() {
        String messageTime = formatter.format(new Date());
        Message passedMessage = new Message(null, userId, null, messageText, null);
        MessageView expectedMessage = new MessageView(userId, messageText, messageTime);
        ResponseEntity<?> expectedResponse = ResponseEntity.ok(expectedMessage);
        Mockito.when(userService.isUserExist(userId)).thenReturn(true);
        doAnswer(invocation -> null).when(userService).getAllUsers();
        passedMessage.setSendTime(messageTime);
        Mockito.when(messageService.create(passedMessage)).thenReturn(expectedMessage);

        ResponseEntity<?> actualResponse = messageController.create(passedMessage);

        verify(userService, times(1)).getAllUsers();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @Timeout(value = 1000, unit = MILLISECONDS)
    void createSuccessfulPrivateChatMessage() {
        String messageTime = formatter.format(new Date());
        Message passedMessage = new Message(null, userId, chatId, messageText, null);
        MessageView expectedMessage = new MessageView(userId, messageText, messageTime);
        ResponseEntity<?> expectedResponse = ResponseEntity.ok(expectedMessage);
        Mockito.when(userService.isUserExist(userId)).thenReturn(true);
        Mockito.when(chatService.isPrivateChatExist(chatId)).thenReturn(true);
        Mockito.when(chatService.isUserInPrivateChat(Mockito.any(), eq(chatId))).thenReturn(true);
        Mockito.when(chatService.getByChatId(chatId)).thenReturn(new Chat());
        passedMessage.setSendTime(messageTime);
        Mockito.when(messageService.create(passedMessage)).thenReturn(expectedMessage);

        ResponseEntity<?> actualResponse = messageController.create(passedMessage);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void createFailedUserNotFound() {
        Message passedMessage = new Message(null, userId, null, messageText, null);
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.USER_NOT_FOUND);
        Mockito.when(userService.isUserExist(userId)).thenReturn(false);

        ResponseEntity<?> actualResponse = messageController.create(passedMessage);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void createFailedTextNotFound() {
        Message passedMessage = new Message(null, userId, null, null, null);
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.TEXT_NOT_FOUND);
        Mockito.when(userService.isUserExist(userId)).thenReturn(true);

        ResponseEntity<?> actualResponse = messageController.create(passedMessage);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void createFailedChatNotFound() {
        Message passedMessage = new Message(null, userId, chatId, messageText, null);
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.CHAT_NOT_FOUND);
        Mockito.when(userService.isUserExist(userId)).thenReturn(true);
        Mockito.when(chatService.isPrivateChatExist(chatId)).thenReturn(false);
        Mockito.when(chatService.isUserInPrivateChat(Mockito.any(), eq(chatId))).thenReturn(false);

        ResponseEntity<?> actualResponse = messageController.create(passedMessage);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void createFailedUserNotInChat() {
        Message passedMessage = new Message(null, userId, chatId, messageText, null);
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.USER_NOT_IN_CHAT);

        Mockito.when(userService.isUserExist(userId)).thenReturn(true);
        Mockito.when(chatService.isPrivateChatExist(chatId)).thenReturn(true);
        Mockito.when(chatService.isUserInPrivateChat(Mockito.any(), eq(chatId))).thenReturn(false);

        ResponseEntity<?> actualResponse = messageController.create(passedMessage);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void readSuccessfulGeneralChatMessages() {
        String messageTime = formatter.format(new Date());
        List<MessageView> expectedList = new ArrayList<>();
        expectedList.add(new MessageView(userId, messageText, messageTime));
        expectedList.add(new MessageView(userId + 1, messageText, messageTime));
        ResponseEntity<?> expectedResponse = ResponseEntity.ok(expectedList);
        Mockito.when(userService.isUserExist(userId)).thenReturn(true);
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            assertEquals(Mockito.any(), arg0);
            Integer arg1 = invocation.getArgument(1);
            assertNull(arg1);
            return null;
        }).when(messageService).readMessages(Mockito.any(), eq(null));
        Mockito.when(messageService.getAllByChatId(null)).thenReturn(expectedList);

        ResponseEntity<?> actualResponse = messageController.read(userId, null);

        verify(messageService, times(1)).readMessages(Mockito.any(), eq(null));
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void readSuccessfulPrivateChatMessages() {
        String messageTime = formatter.format(new Date());
        List<MessageView> expectedList = new ArrayList<>();
        expectedList.add(new MessageView(userId, messageText, messageTime));
        expectedList.add(new MessageView(userId + 1, messageText, messageTime));
        ResponseEntity<?> expectedResponse = ResponseEntity.ok(expectedList);
        Mockito.when(userService.isUserExist(userId)).thenReturn(true);
        Mockito.when(chatService.isPrivateChatExist(chatId)).thenReturn(true);
        Mockito.when(chatService.isUserInPrivateChat(Mockito.any(), eq(chatId))).thenReturn(true);
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            assertEquals(Mockito.any(), arg0);
            Integer arg1 = invocation.getArgument(1);
            assertEquals(chatId, arg1);
            return null;
        }).when(messageService).readMessages(Mockito.any(), eq(chatId));
        Mockito.when(messageService.getAllByChatId(chatId)).thenReturn(expectedList);

        ResponseEntity<?> actualResponse = messageController.read(userId, chatId);

        verify(messageService, times(1)).readMessages(Mockito.any(), eq(chatId));
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void readFailedUserNotFound() {
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.USER_NOT_FOUND);
        Mockito.when(userService.isUserExist(userId)).thenReturn(false);

        ResponseEntity<?> actualResponse = messageController.read(userId, null);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void readFailedChatNotFound() {
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.CHAT_NOT_FOUND);
        Mockito.when(userService.isUserExist(userId)).thenReturn(true);
        Mockito.when(chatService.isPrivateChatExist(chatId)).thenReturn(false);
        Mockito.when(chatService.isUserInPrivateChat(Mockito.any(), eq(chatId))).thenReturn(true);

        ResponseEntity<?> actualResponse = messageController.read(userId, chatId);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void readFailedUserNotInChat() {
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.USER_NOT_IN_CHAT);
        Mockito.when(userService.isUserExist(userId)).thenReturn(true);
        Mockito.when(chatService.isPrivateChatExist(chatId)).thenReturn(true);
        Mockito.when(chatService.isUserInPrivateChat(Mockito.any(), eq(chatId))).thenReturn(false);

        ResponseEntity<?> actualResponse = messageController.read(userId, chatId);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getUnreadMessagesSuccessfulGeneralChat() {
        String someTime = "2021-07-14 13:09:00";
        List<MessageView> expectedList = new ArrayList<>();
        expectedList.add(new MessageView(userId, messageText, someTime));
        ResponseEntity<?> expectedResponse = ResponseEntity.ok(expectedList);
        Mockito.when(userService.isUserExist(userId)).thenReturn(true);
        Mockito.when(messageService.readMessages(Mockito.any(), eq(null))).thenReturn(expectedList);

        ResponseEntity<?> actualResponse = messageController.getUnreadMessages(userId, null);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getUnreadMessagesSuccessfulPrivateChat() {
        String someTime = "2021-07-14 13:09:00";
        List<MessageView> expectedList = new ArrayList<>();
        expectedList.add(new MessageView(userId, messageText, someTime));
        ResponseEntity<?> expectedResponse = ResponseEntity.ok(expectedList);
        Mockito.when(userService.isUserExist(userId)).thenReturn(true);
        Mockito.when(chatService.isPrivateChatExist(chatId)).thenReturn(true);
        Mockito.when(chatService.isUserInPrivateChat(Mockito.any(), eq(chatId))).thenReturn(true);
        Mockito.when(messageService.readMessages(Mockito.any(), eq(chatId))).thenReturn(expectedList);

        ResponseEntity<?> actualResponse = messageController.getUnreadMessages(userId, chatId);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getUnreadMessagesFailedUserNotFound() {
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.USER_NOT_FOUND);
        Mockito.when(userService.isUserExist(userId)).thenReturn(false);

        ResponseEntity<?> actualResponse = messageController.getUnreadMessages(userId, null);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getUnreadMessagesFailedChatNotFound() {
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.CHAT_NOT_FOUND);
        Mockito.when(userService.isUserExist(userId)).thenReturn(true);
        Mockito.when(chatService.isPrivateChatExist(chatId)).thenReturn(false);
        Mockito.when(chatService.isUserInPrivateChat(Mockito.any(), eq(chatId))).thenReturn(true);

        ResponseEntity<?> actualResponse = messageController.getUnreadMessages(userId, chatId);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getUnreadMessagesFailedUserNotInChat() {
        ResponseEntity<?> expectedResponse = ResponseEntity.internalServerError().body(ValidationResult.USER_NOT_IN_CHAT);
        Mockito.when(userService.isUserExist(userId)).thenReturn(true);
        Mockito.when(chatService.isPrivateChatExist(chatId)).thenReturn(true);
        Mockito.when(chatService.isUserInPrivateChat(Mockito.any(), eq(chatId))).thenReturn(false);

        ResponseEntity<?> actualResponse = messageController.getUnreadMessages(userId, chatId);

        assertEquals(expectedResponse, actualResponse);
    }
}
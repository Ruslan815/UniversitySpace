package ru.ruslan.diploma.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ruslan.diploma.error.ErrorHandler;
import ru.ruslan.diploma.error.ValidationResult;
import ru.ruslan.diploma.model.Chat;
import ru.ruslan.diploma.model.Message;
import ru.ruslan.diploma.service.ChatService;
import ru.ruslan.diploma.service.MessageService;
import ru.ruslan.diploma.service.UserService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

@RestController
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;
    private final ChatService chatService;

    public MessageController(MessageService messageService, UserService userService, ChatService chatService) {
        this.messageService = messageService;
        this.userService = userService;
        this.chatService = chatService;
    }

    @PostMapping("/message")
    public ResponseEntity<?> create(@RequestBody Message someMessage) {
        Integer userId = someMessage.getUserId();
        Integer chatId = someMessage.getChatId();
        boolean isUserExist = userService.isUserExist(userId);
        boolean isChatExist = (chatId == null || chatService.isPrivateChatExist(chatId));
        boolean isUserInChat = (chatId == null || (isUserExist && chatService.isUserInPrivateChat(userService.getUser(userId), chatId)));

        ValidationResult returnedRequestStatus = ErrorHandler.validateMessage(someMessage, isUserExist, isChatExist, isUserInChat);
        if (returnedRequestStatus != ValidationResult.NO_ERROR) {
            return ResponseEntity.internalServerError().body(returnedRequestStatus);
        }

        long currentTimeInMillis = System.currentTimeMillis();
        long delaySec = 0;
        if (someMessage.getDelaySec() != null) {
            delaySec = someMessage.getDelaySec();
        }
        someMessage.setSendTimeSec(currentTimeInMillis / 1000 + delaySec);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        someMessage.setSendTime(formatter.format(currentTimeInMillis + delaySec * 1000));

        if (chatId == null) {
            someMessage.setUsersWhoDidNotRead(userService.getAllUsers());
        } else {
            Chat chat = chatService.getByChatId(chatId);
            someMessage.setUsersWhoDidNotRead(new ArrayList<>(chat.getChatMembers()));
        }

        return ResponseEntity.ok(messageService.create(someMessage));
    }

    @GetMapping("/messages")
    public ResponseEntity<?> read(@RequestParam Integer userId, @RequestParam(required = false) Integer chatId) {
        boolean isUserExist = userService.isUserExist(userId);
        boolean isChatExist = (chatId == null || chatService.isPrivateChatExist(chatId));
        boolean isUserInChat = (chatId == null || (isUserExist && chatService.isUserInPrivateChat(userService.getUser(userId), chatId)));
        ResponseEntity<?> responseEntity;
        if (isUserExist && isChatExist && isUserInChat) {
            messageService.readMessages(userService.getUser(userId), chatId); // Marks unread messages in this chat for this user as read
            responseEntity = ResponseEntity.ok(messageService.getAllByChatId(chatId));
        } else if (!isUserExist) {
            responseEntity = ResponseEntity.internalServerError().body(ValidationResult.USER_NOT_FOUND);
        } else if (!isChatExist) {
            responseEntity = ResponseEntity.internalServerError().body(ValidationResult.CHAT_NOT_FOUND);
        } else {
            responseEntity = ResponseEntity.internalServerError().body(ValidationResult.USER_NOT_IN_CHAT);
        }

        return responseEntity;
    }

    @GetMapping("/messages/unread")
    public ResponseEntity<?> getUnreadMessages(@RequestParam Integer userId, @RequestParam(required = false) Integer chatId) {
        boolean isUserExist = userService.isUserExist(userId);
        boolean isChatExist = (chatId == null || chatService.isPrivateChatExist(chatId));
        boolean isUserInChat = (chatId == null || (isUserExist && chatService.isUserInPrivateChat(userService.getUser(userId), chatId)));
        ResponseEntity<?> responseEntity;
        if (isUserExist && isChatExist && isUserInChat) {
            responseEntity = ResponseEntity.ok(messageService.readMessages(userService.getUser(userId), chatId));
        } else if (!isUserExist) {
            responseEntity = ResponseEntity.internalServerError().body(ValidationResult.USER_NOT_FOUND);
        } else if (!isChatExist) {
            responseEntity = ResponseEntity.internalServerError().body(ValidationResult.CHAT_NOT_FOUND);
        } else {
            responseEntity = ResponseEntity.internalServerError().body(ValidationResult.USER_NOT_IN_CHAT);
        }

        return responseEntity;
    }
}

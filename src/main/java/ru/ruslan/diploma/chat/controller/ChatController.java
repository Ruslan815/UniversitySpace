package ru.ruslan.diploma.chat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ruslan.diploma.chat.service.UserService;
import ru.ruslan.diploma.chat.error.ValidationResult;
import ru.ruslan.diploma.chat.model.Chat;
import ru.ruslan.diploma.chat.model.ChatMember;
import ru.ruslan.diploma.chat.model.ChatView;
import ru.ruslan.diploma.chat.parser.RSSFeedParser;
import ru.ruslan.diploma.chat.service.ChatService;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
public class ChatController {
    private final ChatService chatService;
    private final UserService userService;

    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @PostMapping("/chat")
    public ResponseEntity<?> create(@RequestBody Chat someChat) {
        ResponseEntity<?> responseEntity;
        ChatView response;
        try {
            if(someChat.getRssLink() != null) {
                RSSFeedParser parser = new RSSFeedParser(someChat.getRssLink());
                parser.readFeed();
            }
            response = chatService.create(someChat);
            responseEntity = ResponseEntity.ok(response);
        } catch (MalformedURLException e) {
            responseEntity = ResponseEntity.internalServerError().body(ValidationResult.MALFORMED_RSS_LINK);
        } catch (IOException e) {
            responseEntity = ResponseEntity.internalServerError().body(ValidationResult.URL_CONNECTION_FAILED);
        } catch (XMLStreamException e) {
            responseEntity = ResponseEntity.internalServerError().body(ValidationResult.UNKNOWN_ERROR);
        } catch (Exception e) {
            responseEntity = ResponseEntity.internalServerError().body(ValidationResult.CHAT_NAME_NOT_FOUND);
        }
        return responseEntity;
    }

    @GetMapping("/chats")
    public List<ChatView> get() {
        return chatService.getAll();
    }

    @PostMapping("/chat/enter")
    public ResponseEntity<?> enterChat(@RequestBody ChatMember chatMember) {
        ResponseEntity<?> response;
        Integer userId = chatMember.getUserId();
        Integer chatId = chatMember.getChatId();

        if (!userService.isUserExist(userId)) {
            response = ResponseEntity.internalServerError().body(ValidationResult.USER_NOT_FOUND);
        } else if (!chatService.isPrivateChatExist(chatId)) {
            response = ResponseEntity.internalServerError().body(ValidationResult.CHAT_NOT_FOUND);
        } else {
            if (chatService.enterChat(userService.getUser(userId), chatId)) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    JsonNode jsonResponse = objectMapper.readTree("{\"enterStatus\": \"You successfully entered the chat №" + chatId + "\"}");
                    response = ResponseEntity.ok(jsonResponse);
                } catch (JsonProcessingException e) {
                    response = ResponseEntity.internalServerError().body(ValidationResult.UNKNOWN_ERROR);
                }
            } else {
                response = ResponseEntity.internalServerError().body(ValidationResult.USER_ALREADY_IN_CHAT);
            }
        }
        return response;
    }

    @PostMapping("/chat/leave")
    public ResponseEntity<?> leaveChat(@RequestBody ChatMember chatMember) {
        ResponseEntity<?> response;
        Integer userId = chatMember.getUserId();
        Integer chatId = chatMember.getChatId();

        if (!userService.isUserExist(userId)) {
            response = ResponseEntity.internalServerError().body(ValidationResult.USER_NOT_FOUND);
        } else if (!chatService.isPrivateChatExist(chatId)) {
            response = ResponseEntity.internalServerError().body(ValidationResult.CHAT_NOT_FOUND);
        } else {
            if (chatService.leaveChat(userService.getUser(userId), chatId)) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    JsonNode jsonResponse = objectMapper.readTree("{\"leaveStatus\": \"You successfully left the chat №" + chatId + "\"}");
                    response = ResponseEntity.ok(jsonResponse);
                } catch (JsonProcessingException e) {
                    response = ResponseEntity.internalServerError().body(ValidationResult.UNKNOWN_ERROR);
                }
            } else {
                response = ResponseEntity.internalServerError().body(ValidationResult.USER_NOT_IN_CHAT);
            }
        }
        return response;
    }
}

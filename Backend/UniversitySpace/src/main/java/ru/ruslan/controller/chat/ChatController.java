package ru.ruslan.controller.chat;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.ruslan.entity.chat.Chat;
import ru.ruslan.entity.chat.ChatMember;
import ru.ruslan.entity.chat.ChatView;
import ru.ruslan.entity.user.User;
import ru.ruslan.service.chat.ChatService;
import ru.ruslan.service.user.SecurityUserService;
import ru.ruslan.service.user.UserService;

import java.util.List;

@Controller
public class ChatController {
    private final ChatService chatService;
    private final UserService userService;

    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @GetMapping("/chat")
    public String getChatPage(@RequestParam Long chatId) {
        try {
            Long userId = SecurityUserService.getCurrentUserId();
            User user = userService.findUserById(userId);
            if (chatService.isUserInPrivateChat(user, chatId)) {
                return "html/chat/chatPage";
            } else {
                return "html/chat/enterChatPage";
            }
        } catch (Exception ignored) {
            return "error";
        }

    }

    @PostMapping("/api/chat")
    public ResponseEntity<?> create(@RequestBody Chat someChat) {
        ResponseEntity<?> responseEntity;
        ChatView response;

        if (chatService.isChatNameExists(someChat.getName())) {
            return ResponseEntity.status(500).body("CHAT NAME IS BUSY");
        }

        try {
            response = chatService.create(someChat);
            responseEntity = ResponseEntity.ok(response);
        } catch (Exception e) {
            responseEntity = ResponseEntity.status(500).body("CHAT NAME NOT FOUND");
        }
        return responseEntity;
    }

    @GetMapping("/chats")
    public String getAllChatsPage() {
        return "html/chat/allChatsPage";
    }

    @GetMapping("/api/chats")
    @ResponseBody
    public List<ChatView> getAllChats() {
        return chatService.getAll();
    }

    @PostMapping("/api/chat/enter")
    public ResponseEntity<?> enterChat(@RequestBody ChatMember chatMember) {
        ResponseEntity<?> response;
        Long userId = chatMember.getUserId();
        Long chatId = chatMember.getChatId();

        if (chatService.enterChat(userService.findUserById(userId), chatId)) {
            response = ResponseEntity.ok().body(userService.findUserById(userId).getUsername());
        } else {
            response = ResponseEntity.status(500).body("User already in chat!");
        }
        return response;
    }

    @PostMapping("/api/chat/leave")
    public ResponseEntity<?> leaveChat(@RequestBody ChatMember chatMember) {
        ResponseEntity<?> response;
        Long userId = chatMember.getUserId();
        Long chatId = chatMember.getChatId();

        if (chatService.leaveChat(userService.findUserById(userId), chatId)) {
            response = ResponseEntity.ok().body(userService.findUserById(userId).getUsername());
        } else {
            response = ResponseEntity.status(500).body("User not in chat!");
        }
        return response;
    }
}

package ru.ruslan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.ruslan.entity.Chat;
import ru.ruslan.entity.ChatMember;
import ru.ruslan.entity.ChatView;
import ru.ruslan.service.ChatService;
import ru.ruslan.service.UserService;

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
    public String getChatPage(@RequestParam Long userId, @RequestParam Long chatId) {
        return "html/chatPage";
    }

    @PostMapping("/chat")
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
        return "html/allChatsPage";
    }

    @GetMapping("/chatsList")
    @ResponseBody
    public List<ChatView> get() {
        return chatService.getAll();
    }

    @PostMapping("/chat/enter")
    public ResponseEntity<?> enterChat(@RequestBody ChatMember chatMember) {
        ResponseEntity<?> response = ResponseEntity.ok("Successfully entered the chat!");
        Integer userId = chatMember.getUserId();
        Integer chatId = chatMember.getChatId();
        chatService.enterChat(userService.findUserById((long)userId), chatId);

        return response;
    }

    @PostMapping("/chat/leave")
    public ResponseEntity<?> leaveChat(@RequestBody ChatMember chatMember) {
        ResponseEntity<?> response = ResponseEntity.ok("Successfully left the chat!");
        Integer userId = chatMember.getUserId();
        Integer chatId = chatMember.getChatId();
        chatService.leaveChat(userService.findUserById((long)userId), chatId);

        return response;
    }
}

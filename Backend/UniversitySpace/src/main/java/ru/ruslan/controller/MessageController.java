package ru.ruslan.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ruslan.entity.Chat;
import ru.ruslan.entity.Message;
import ru.ruslan.service.ChatService;
import ru.ruslan.service.MessageService;
import ru.ruslan.service.UserService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

@CrossOrigin
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

        long currentTimeInMillis = System.currentTimeMillis();
        someMessage.setSendTimeSec(currentTimeInMillis / 1000);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        someMessage.setSendTime(formatter.format(currentTimeInMillis));

        return ResponseEntity.ok(messageService.create(someMessage));
    }

    @GetMapping("/messages")
    public ResponseEntity<?> read(@RequestParam(required = false) Integer userId, @RequestParam Integer chatId) {
        ResponseEntity<?> responseEntity;
        responseEntity = ResponseEntity.ok(messageService.getAllByChatId(chatId));

        return responseEntity;
    }
}

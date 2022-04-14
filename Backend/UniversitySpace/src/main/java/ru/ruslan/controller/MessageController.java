package ru.ruslan.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import ru.ruslan.entity.Chat;
import ru.ruslan.entity.Message;
import ru.ruslan.service.ChatService;
import ru.ruslan.service.MessageService;
import ru.ruslan.service.UserService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

@RestController
public class MessageController {
    @Autowired
    private final MessageService messageService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final ChatService chatService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

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

        Chat chat = chatService.getByChatId(chatId);
        someMessage.setUsersWhoDidNotRead(new ArrayList<>(chat.getChatMembers()));

        return ResponseEntity.ok(messageService.create(someMessage));
    }

    @GetMapping("/messages")
    public ResponseEntity<?> read(@RequestParam(required = false) Integer userId, @RequestParam Integer chatId) {
        ResponseEntity<?> responseEntity;
        responseEntity = ResponseEntity.ok(messageService.getAllByChatId(chatId));

        return responseEntity;
    }

    @GetMapping("/messages/unread")
    public DeferredResult<ResponseEntity<?>> getUnreadMessages(@RequestParam Integer userId, @RequestParam Integer chatId) {
        DeferredResult<ResponseEntity<?>> output = new DeferredResult<>();

        //executorService.execute(() -> {
        while (!messageService.isUnreadMessagesExist(userService.findUserById((long) userId), chatId)) {
            try {
                Thread.sleep(150);
                System.out.println(Thread.currentThread().getId() + ") sleep");
            } catch (InterruptedException ignored) {
            }
        }

        System.out.println(Thread.currentThread().getId() + ") complete");
        ResponseEntity<?> someResponse = ResponseEntity.ok(messageService.readMessages(userService.findUserById((long) userId), chatId));
        output.setResult(someResponse);
        //});

        return output;
    }
}

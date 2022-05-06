package ru.ruslan.controller.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import ru.ruslan.entity.chat.Chat;
import ru.ruslan.entity.chat.Message;
import ru.ruslan.service.chat.ChatService;
import ru.ruslan.service.chat.MessageService;
import ru.ruslan.service.user.UserService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

@RestController
public class MessageController {
    @Autowired
    private final MessageService messageService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final ChatService chatService;
    // private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public MessageController(MessageService messageService, UserService userService, ChatService chatService) {
        this.messageService = messageService;
        this.userService = userService;
        this.chatService = chatService;
    }

    @PostMapping("/api/message")
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

    @GetMapping("/api/messages")
    public ResponseEntity<?> read(@RequestParam Integer chatId) {
        ResponseEntity<?> responseEntity;
        responseEntity = ResponseEntity.ok(messageService.getAllByChatId(chatId));

        return responseEntity;
    }

    @GetMapping("/api/messages/unread")
    public DeferredResult<ResponseEntity<?>> getUnreadMessages(@RequestParam Integer userId, @RequestParam Integer chatId) {
        DeferredResult<ResponseEntity<?>> output = new DeferredResult<>();

        output.onError((Throwable t) -> {
            output.setErrorResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error"));
        });

        Thread newThread = new Thread(() -> {
            int timeoutCounter = 0;
            while (!messageService.isUnreadMessagesExist(userService.findUserById((long) userId), chatId)) {
                if (timeoutCounter++ > 165) {
                    output.setErrorResult(ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("Request timeout"));
                    return;
                }

                try {
                    Thread.sleep(180);
                } catch (InterruptedException ignored) {
                }
            }

            ResponseEntity<?> someResponse = ResponseEntity
                    .ok(messageService.readMessages(userService.findUserById((long) userId), chatId));
            output.setResult(someResponse);
        });
        newThread.start();

        return output;
    }
}

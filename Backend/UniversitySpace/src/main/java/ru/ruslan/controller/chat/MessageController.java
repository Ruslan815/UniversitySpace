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
        Long chatId = someMessage.getChatId();

        long currentTimeInMillis = System.currentTimeMillis();
        someMessage.setSendTimeSec(currentTimeInMillis / 1000);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        someMessage.setSendTime(formatter.format(currentTimeInMillis));

        Chat chat = chatService.getByChatId(chatId);
        someMessage.setUsersWhoDidNotRead(new ArrayList<>(chat.getChatMembers()));

        return ResponseEntity.ok(messageService.create(someMessage));
    }

    @GetMapping("/api/messages")
    public ResponseEntity<?> getChatMessages(@RequestParam Long chatId, @RequestParam Long userId) {
        return ResponseEntity.ok(messageService.getAllByChatId(chatId, userId));
    }

    @GetMapping("/api/messages/unread")
    public DeferredResult<ResponseEntity<?>> getUnreadMessages(@RequestParam Long userId, @RequestParam Long chatId) {
        DeferredResult<ResponseEntity<?>> output = new DeferredResult<>();
        if (userService.isUserListening(userId)) {
            output.setResult(ResponseEntity.status(501).body("Wait please!"));
            return output;
        }
        userService.addListeningUser(userId);

        output.onError((Throwable t) -> {
            output.setErrorResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error"));
        });

        Thread newThread = new Thread(() -> {
            int timeoutCounter = 0;
            while (!messageService.isUnreadMessagesExist(userService.findUserById(userId), chatId)) {
                if (timeoutCounter++ > 60) {
                    userService.removeListeningUser(userId);
                    output.setErrorResult(ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("Request timeout"));
                    return;
                }

                try {
                    Thread.sleep(180);
                } catch (InterruptedException ignored) {
                }
            }

            ResponseEntity<?> someResponse = ResponseEntity
                    .ok(messageService.readMessages(userService.findUserById(userId), chatId));
            userService.removeListeningUser(userId);
            output.setResult(someResponse);
        });
        newThread.start();

        return output;
    }
}

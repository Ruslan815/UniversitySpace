package ru.ruslan.diploma.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.ruslan.diploma.error.ValidationResult;
import ru.ruslan.diploma.model.Attach;
import ru.ruslan.diploma.model.AttachRequest;
import ru.ruslan.diploma.service.AttachService;
import ru.ruslan.diploma.service.ChatService;
import ru.ruslan.diploma.service.UserService;

import java.io.IOException;

@Controller
public class AttachController {

    private final UserService userService;
    private final ChatService chatService;
    private final AttachService attachService;

    public AttachController(UserService userService, ChatService chatService, AttachService attachService) {
        this.userService = userService;
        this.chatService = chatService;
        this.attachService = attachService;
    }

    @PostMapping("/attach")
    public ResponseEntity<?> postAttach(@ModelAttribute AttachRequest attach) {
        Integer userId = attach.getUserId();
        Integer chatId = attach.getChatId();
        boolean isUserExist = userService.isUserExist(userId);
        boolean isChatExist = (chatId == null || chatService.isPrivateChatExist(chatId));
        boolean isUserInChat = (chatId == null || (isUserExist && chatService.isUserInPrivateChat(userService.getUser(userId), chatId)));
        ResponseEntity<?> responseEntity;
        if (isUserExist && isChatExist && isUserInChat) {
            try {
                responseEntity = ResponseEntity.ok(attachService.create(attach));
            } catch (IOException e) {
                responseEntity = ResponseEntity.internalServerError().body(ValidationResult.UNKNOWN_ERROR);
            }
        } else if (!isUserExist) {
            responseEntity = ResponseEntity.internalServerError().body(ValidationResult.USER_NOT_FOUND);
        } else if (!isChatExist) {
            responseEntity = ResponseEntity.internalServerError().body(ValidationResult.CHAT_NOT_FOUND);
        } else {
            responseEntity = ResponseEntity.internalServerError().body(ValidationResult.USER_NOT_IN_CHAT);
        }

        return responseEntity;
    }

    @GetMapping("/attach/{fileName}")
    public ResponseEntity<?> getAttach(@PathVariable(name = "fileName") String fileName) {
        Attach attach = attachService.getByFileName(fileName);
        if (attach == null) {
            return ResponseEntity.internalServerError().body(ValidationResult.FILE_NOT_FOUND);
        }
        String contentType = attach.getContentType();
        byte[] content = attach.getFile();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(content);
    }
}

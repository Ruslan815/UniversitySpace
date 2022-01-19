package ru.ruslan.diploma.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.ruslan.diploma.model.Message;
import ru.ruslan.diploma.model.User;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ValidationResult handleAnyException(Throwable exception) {
        return ValidationResult.UNKNOWN_ERROR;
    }

    public static ValidationResult validateUser(User someUser) {
        if (someUser.getFirstName() == null && someUser.getLastName() == null) {
            return ValidationResult.FIRSTNAME_AND_LASTNAME_NOT_FOUND;
        } else if (someUser.getFirstName() == null) {
            return ValidationResult.FIRSTNAME_NOT_FOUND;
        } else if (someUser.getLastName() == null) {
            return ValidationResult.LASTNAME_NOT_FOUND;
        }
        return ValidationResult.NO_ERROR;
    }

    public static ValidationResult validateMessage(Message someMessage, boolean isUserExist, boolean isChatExist, boolean isUserInChat) {
        if (!isUserExist) {
            return ValidationResult.USER_NOT_FOUND;
        }
        if (someMessage.getText() == null) {
            return ValidationResult.TEXT_NOT_FOUND;
        }
        if (!isChatExist) {
            return ValidationResult.CHAT_NOT_FOUND;
        }
        if (!isUserInChat) {
            return ValidationResult.USER_NOT_IN_CHAT;
        }
        return ValidationResult.NO_ERROR;
    }
}

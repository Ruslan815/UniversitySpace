package ru.ruslan.diploma.error;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ruslan.diploma.model.Message;
import ru.ruslan.diploma.model.User;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ErrorHandlerTest {

    private final String firstName = "cat";
    private final String lastName = "dog";
    private final Integer userId = 1;
    private final String text = "some text";

    @Test
    void validateUserSuccessful() {
        User passedUser = new User(null, firstName, lastName);
        ValidationResult expected = ValidationResult.NO_ERROR;

        ValidationResult actual = ErrorHandler.validateUser(passedUser);

        assertEquals(expected, actual);
    }

    @Test
    void validateUserFailedFirstNameAndLastNameNotFound() {
        User passedUser = new User(null, null, null);
        ValidationResult expected = ValidationResult.FIRSTNAME_AND_LASTNAME_NOT_FOUND;

        ValidationResult actual = ErrorHandler.validateUser(passedUser);

        assertEquals(expected, actual);
    }

    @Test
    void validateUserFailedFirstNameNotFound() {
        User passedUser = new User(null, null, lastName);
        ValidationResult expected = ValidationResult.FIRSTNAME_NOT_FOUND;

        ValidationResult actual = ErrorHandler.validateUser(passedUser);

        assertEquals(expected, actual);
    }

    @Test
    void validateUserFailedLastNameNotFound() {
        User passedUser = new User(null, firstName, null);
        ValidationResult expected = ValidationResult.LASTNAME_NOT_FOUND;

        ValidationResult actual = ErrorHandler.validateUser(passedUser);

        assertEquals(expected, actual);
    }

    @Test
    void validateMessageSuccessful() {
        Message passedMessage = new Message(null, userId, null, text, null);
        ValidationResult expected = ValidationResult.NO_ERROR;

        ValidationResult actual = ErrorHandler.validateMessage(passedMessage, true, true, true);

        assertEquals(expected, actual);
    }

    @Test
    void validateMessageFailedTextNotFound() {
        Message passedMessage = new Message(null, userId, null, null, null);
        ValidationResult expected = ValidationResult.TEXT_NOT_FOUND;

        ValidationResult actual = ErrorHandler.validateMessage(passedMessage, true, true, true);

        assertEquals(expected, actual);
    }

    @Test
    void validateMessageFailedUserNotFound() {
        Message passedMessage = new Message(null, userId, null, text, null);
        ValidationResult expected = ValidationResult.USER_NOT_FOUND;

        ValidationResult actual = ErrorHandler.validateMessage(passedMessage, false, true, true);

        assertEquals(expected, actual);
    }

    @Test
    void validateMessageFailedChatNotFound() {
        Message passedMessage = new Message(null, userId, null, text, null);
        ValidationResult expected = ValidationResult.CHAT_NOT_FOUND;

        ValidationResult actual = ErrorHandler.validateMessage(passedMessage, true, false, true);

        assertEquals(expected, actual);
    }

    @Test
    void validateMessageFailedUserNotInChat() {
        Message passedMessage = new Message(null, userId, null, text, null);
        ValidationResult expected = ValidationResult.USER_NOT_IN_CHAT;

        ValidationResult actual = ErrorHandler.validateMessage(passedMessage, true, true, false);

        assertEquals(expected, actual);
    }
}
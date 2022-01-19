package ru.ruslan.diploma.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ValidationResult {
    USER_NOT_FOUND,
    FIRSTNAME_NOT_FOUND,
    LASTNAME_NOT_FOUND,
    FIRSTNAME_AND_LASTNAME_NOT_FOUND,
    TEXT_NOT_FOUND,
    CHAT_NOT_FOUND,
    CHAT_NAME_NOT_FOUND,
    USER_ALREADY_IN_CHAT,
    USER_NOT_IN_CHAT,
    MALFORMED_RSS_LINK,
    URL_CONNECTION_FAILED,
    FILE_NOT_FOUND,
    UNKNOWN_ERROR,
    NO_ERROR,
    ;

    @JsonSerialize
    String getCode() {
        return name();
    }
}

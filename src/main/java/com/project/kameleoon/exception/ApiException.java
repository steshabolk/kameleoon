package com.project.kameleoon.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    private final String messageProp;
    private final String[] messageArgs;
    private final String code;
    private final HttpStatus status;

    public ApiException(String messageProp, String[] messageArgs, String code, HttpStatus status) {
        this.messageProp = messageProp;
        this.messageArgs = messageArgs;
        this.code = code;
        this.status = status;
    }
}

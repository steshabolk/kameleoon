package com.project.kameleoon.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;

public enum ApiError {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "ex.api.userNotFound"),
    QUOTE_NOT_FOUND(HttpStatus.NOT_FOUND, "ex.api.quoteNotFound"),
    RANDOM_QUOTE_NOT_FOUND(HttpStatus.NOT_FOUND, "ex.api.randomQuoteNotFound");

    private final HttpStatus httpStatus;
    private final String messageProp;

    ApiError(HttpStatus httpStatus, String messageProp) {
        this.httpStatus = httpStatus;
        this.messageProp = messageProp;
    }

    public ApiException toException(Object... args) {
        return new ApiException(messageProp, Arrays.stream(args).map(Object::toString).toArray(String[]::new), name(), httpStatus);
    }
}

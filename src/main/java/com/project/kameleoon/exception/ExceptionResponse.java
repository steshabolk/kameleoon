package com.project.kameleoon.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ExceptionResponse {

    private final String code;

    private final String message;

    private final LocalDateTime timestamp;
}

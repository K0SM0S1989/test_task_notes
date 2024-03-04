package com.krd.TestTaskNotes.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BadRequestException extends Exception {
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }
}

package com.example.bcsd.exception;

public class NullRequestException extends RuntimeException {
    public NullRequestException(String message) {
        super(message);
    }
}
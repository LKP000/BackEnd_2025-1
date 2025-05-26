package com.example.bcsd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NotFound. " + ex.getMessage());
    }

    @ExceptionHandler(DeletionNotAllowedException.class)
    public ResponseEntity<String> handleDeletionNotAllowedException(DeletionNotAllowedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제 불가. " + ex.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 이메일. " + ex.getMessage());
    }

    @ExceptionHandler(InvalidReferenceException.class)
    public ResponseEntity<String> handleInvalidReferenceException(InvalidReferenceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("허가되지 않은 참조. " + ex.getMessage());
    }

    @ExceptionHandler(NullRequestException.class)
    public ResponseEntity<String> handleInvalidRequestException(NullRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("허가되지 않은 Null 요청. " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnexpectedError(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류. " + ex.getMessage());
    }
}
package edu.agh.sharedshoppinglist.controller;

import edu.agh.sharedshoppinglist.exception.ApplicationException;
import edu.agh.sharedshoppinglist.exception.ErrorCode;
import lombok.Getter;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AbstractExceptionHandler {

    @ExceptionHandler(value = {RuntimeException.class})
    public Error handleUserLoginAlreadyTaken(Exception ex) {
        if (ex instanceof ApplicationException) {
            return new Error(((ApplicationException) ex).getErrorCode());
        }
        return new Error(ErrorCode.UNKNOWN_ERROR);
    }

    @Getter
    private static class Error {
        int code;
        String message;

        public Error(ErrorCode error) {
            this.code = error.getCode();
            this.message = error.getMessage();
        }
    }
}

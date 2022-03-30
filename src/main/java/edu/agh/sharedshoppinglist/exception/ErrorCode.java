package edu.agh.sharedshoppinglist.exception;

public enum ErrorCode {
    USER_LOGIN_ALREADY_TAKEN(101, "User login already taken"),
    INVALID_LOGIN_OR_PASSWORD(102, "Invalid login or password"),
    INVALID_SESSION(103, "Invalid session"),
    INVALID_LIST_CODE(105, "Invalid list code"),
    UNKNOWN_ERROR(999, "Unknown error");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

package com.ung.apiboard.exception;

public class MemberEmailAlreadyExistsException  extends RuntimeException{
    public MemberEmailAlreadyExistsException(String message) {
        super(message);
    }
}

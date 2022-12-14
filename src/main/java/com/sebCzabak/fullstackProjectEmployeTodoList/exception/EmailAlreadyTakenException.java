package com.sebCzabak.fullstackProjectEmployeTodoList.exception;

public class EmailAlreadyTakenException extends RuntimeException{

    public EmailAlreadyTakenException(String email){
        super("Email is already taken.");
    }
}

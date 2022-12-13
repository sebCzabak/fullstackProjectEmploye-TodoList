package com.sebCzabak.fullstackProjectEmployeTodoList.model.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(Long id){
        super("Couldn't find user with id of "+id);
    }
}

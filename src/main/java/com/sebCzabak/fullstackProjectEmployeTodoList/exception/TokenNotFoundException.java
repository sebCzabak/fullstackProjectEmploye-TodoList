package com.sebCzabak.fullstackProjectEmployeTodoList.exception;

public class TokenNotFoundException extends RuntimeException{

    public TokenNotFoundException(String token){
        super("Couldn't find token with number of "+token);
    }
}

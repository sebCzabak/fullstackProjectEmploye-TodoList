package com.sebCzabak.fullstackProjectEmployeTodoList.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class EmailAlreadyTakenAdvice {
    @ResponseBody
    @ExceptionHandler(EmailAlreadyTakenException.class)
    @ResponseStatus(HttpStatus.FOUND)
    public Map<String,String>exceptionHandler(EmailAlreadyTakenException exception){
        Map<String,String>errorMap = new HashMap<>();
        errorMap.put("Error",exception.getMessage());
        return errorMap;
    }
}

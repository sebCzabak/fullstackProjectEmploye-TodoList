package com.sebCzabak.fullstackProjectEmployeTodoList.exception;

public class TaskNotFoundException extends RuntimeException{

    public TaskNotFoundException(Long id){
        super("Couldn't find task with id of "+id);
    }
}

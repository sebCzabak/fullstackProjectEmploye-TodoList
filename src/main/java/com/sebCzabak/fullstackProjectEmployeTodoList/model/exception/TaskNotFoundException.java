package com.sebCzabak.fullstackProjectEmployeTodoList.model.exception;

public class TaskNotFoundException extends RuntimeException{

    public TaskNotFoundException(Long id){
        super("Couldn't find task with id of "+id);
    }
}

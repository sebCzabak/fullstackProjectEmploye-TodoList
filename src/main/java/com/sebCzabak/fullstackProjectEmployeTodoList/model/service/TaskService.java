package com.sebCzabak.fullstackProjectEmployeTodoList.model.service;

import com.sebCzabak.fullstackProjectEmployeTodoList.model.Task.Task;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Task.TaskRepo;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.exception.TaskNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepo taskRepo;

    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    public Task toggleTaskDone(Long id) {
        Task task = taskRepo.findById(id).orElseThrow(()->new TaskNotFoundException(id));
        task.setDone(!task.getDone());
        return taskRepo.save(task);
    }

    public void deleteTask(Long id) {
        Task task = taskRepo.findById(id).orElseThrow(()->new TaskNotFoundException(id));
         taskRepo.delete(task);
    }
}

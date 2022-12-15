package com.sebCzabak.fullstackProjectEmployeTodoList.service;

import com.sebCzabak.fullstackProjectEmployeTodoList.exception.UserNotFoundException;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Employee.Employee;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Employee.EmployeeRepo;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Task.Task;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Task.TaskRepo;
import com.sebCzabak.fullstackProjectEmployeTodoList.exception.TaskNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepo taskRepo;
    private final EmployeeRepo employeeRepo;

    public TaskService(TaskRepo taskRepo,
                       EmployeeRepo employeeRepo) {
        this.taskRepo = taskRepo;
        this.employeeRepo = employeeRepo;
    }

    public Task toggleTaskDone(Long id) {
        Task task = taskRepo.findById(id).orElseThrow(()->new TaskNotFoundException(id));
        task.setDone(!task.getDone());
        return taskRepo.save(task);
    }

    public void deleteTask(Long employeeId,Long taskId) {
        Employee employee = employeeRepo.findById(employeeId).orElseThrow(()->new UserNotFoundException(employeeId));
        Task task = taskRepo.findById(taskId).orElseThrow(()->new TaskNotFoundException(taskId));
        employee.getTaskList().remove(task);
        taskRepo.delete(task);
    }

    public List<Task> findAll() {
        return taskRepo.findAll();
    }

    public Optional<Task> findById(Long id) {
        return taskRepo.findById(id);
    }
}

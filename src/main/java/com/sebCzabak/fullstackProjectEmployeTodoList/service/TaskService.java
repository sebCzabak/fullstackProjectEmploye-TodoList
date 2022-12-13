package com.sebCzabak.fullstackProjectEmployeTodoList.service;

import com.sebCzabak.fullstackProjectEmployeTodoList.exception.UserNotFoundException;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Employee.Employee;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Employee.EmployeeRepo;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Task.Task;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Task.TaskRepo;
import com.sebCzabak.fullstackProjectEmployeTodoList.exception.TaskNotFoundException;
import org.springframework.stereotype.Service;

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

    public void deleteTask(Long employeid,Long taskid) {
        Employee employee = employeeRepo.findById(employeid).orElseThrow(()->new UserNotFoundException(employeid));
        Task task = taskRepo.findById(taskid).orElseThrow(()->new TaskNotFoundException(taskid));
        employee.getTaskList().remove(task);
        taskRepo.delete(task);
    }
}

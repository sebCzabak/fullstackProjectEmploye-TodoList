package com.sebCzabak.fullstackProjectEmployeTodoList.model.controller;


import com.sebCzabak.fullstackProjectEmployeTodoList.model.Employee.Employee;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Task.Task;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.service.EmployeeService;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.service.TaskService;
import com.sebCzabak.fullstackProjectEmployeTodoList.token.RegistrationRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api")
public class EmployeeController {

    public EmployeeController(TaskService taskService, EmployeeService employeeService) {
        this.taskService = taskService;
        this.employeeService = employeeService;
    }

    private final TaskService taskService;
    private final EmployeeService employeeService;


    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable Long id){
        return employeeService.findById(id);
    }

    @PostMapping("/registration")
    public String register(@RequestBody RegistrationRequest request){
        return employeeService.register(request);
    }


    @PostMapping("/employees/{id}/addTask")
    public Employee addTask(@PathVariable Long id,
                            @RequestBody Task task){
        return employeeService.addTask(id,task);
    }
    @DeleteMapping("/id")
    public void deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
    }
    @PostMapping("/tasks/{id}")
    public Task toggleTaskDone(@PathVariable Long id){
        return taskService.toggleTaskDone(id);
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
    }
}

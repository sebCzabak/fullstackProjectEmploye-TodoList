package com.sebCzabak.fullstackProjectEmployeTodoList.controller;


import com.sebCzabak.fullstackProjectEmployeTodoList.model.Employee.Employee;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Task.Task;
import com.sebCzabak.fullstackProjectEmployeTodoList.service.EmployeeService;
import com.sebCzabak.fullstackProjectEmployeTodoList.service.TaskService;
import com.sebCzabak.fullstackProjectEmployeTodoList.request.RegistrationRequest;
import com.sebCzabak.fullstackProjectEmployeTodoList.token.ConfirmationToken.ConfirmationTokenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping(path="/api")
public class EmployeeController {

    public EmployeeController(TaskService taskService, EmployeeService employeeService, ConfirmationTokenService confirmationTokenService) {
        this.taskService = taskService;
        this.employeeService = employeeService;
        this.confirmationTokenService = confirmationTokenService;
    }

    private final TaskService taskService;
    private final EmployeeService employeeService;
    private final ConfirmationTokenService confirmationTokenService;

    @GetMapping("/employees")
    public List<Employee>getAllEmployees(){
        return employeeService.findALl();
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable Long id){
        return employeeService.findById(id);
    }

    @GetMapping("/employee/{id}")
    public Employee getEmployeeById(@PathVariable Long id){
        return employeeService.findById(id);
    }
    @PostMapping("/registration")
    public String register(@RequestBody RegistrationRequest request){
        return employeeService.register(request);
    }


    @PostMapping("/employees/{id}/addTask")
    public void addTask(@PathVariable Long id,
                        @RequestBody Task task){
        employeeService.addTask(id,task);
    }
    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
    }
    @DeleteMapping("/employees/confirmationToken/{id}")
    public void deleteToken(@PathVariable Long id){
        confirmationTokenService.deleteToken(id);
    }
    @PostMapping("/tasks/{id}")
    public Task toggleTaskDone(@PathVariable Long id){
        return taskService.toggleTaskDone(id);
    }

    @DeleteMapping("{employeeId}/tasks/{taskId}")
    public void deleteTask(@PathVariable Long employeeId, @PathVariable Long taskId){
        taskService.deleteTask(employeeId,taskId);
    }

    @PutMapping("/employees/{id}")
    public Employee updateEmployeeInfo(@RequestBody RegistrationRequest newEmployee,@PathVariable Long id){
        return employeeService.updateEmployeeInfo(newEmployee,id);
    }
    @GetMapping("/registration/confirm")
    public String confirm(@RequestParam("token")String token){
        return employeeService.confirmToken(token);
    }

    @GetMapping("/employees/Login")
    public Optional<Employee> loginEmployee(@RequestParam String email, @RequestParam String password ){
        return employeeService.findByEmailAndPassword(email,password);
    }
}

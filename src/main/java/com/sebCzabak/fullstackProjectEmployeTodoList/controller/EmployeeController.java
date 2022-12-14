package com.sebCzabak.fullstackProjectEmployeTodoList.controller;


import com.sebCzabak.fullstackProjectEmployeTodoList.model.Employee.Employee;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Task.Task;
import com.sebCzabak.fullstackProjectEmployeTodoList.service.EmployeeService;
import com.sebCzabak.fullstackProjectEmployeTodoList.service.TaskService;
import com.sebCzabak.fullstackProjectEmployeTodoList.request.RegistrationRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping(path="/api")
public class EmployeeController {

    public EmployeeController(TaskService taskService, EmployeeService employeeService) {
        this.taskService = taskService;
        this.employeeService = employeeService;
    }

    private final TaskService taskService;
    private final EmployeeService employeeService;

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
    public Employee addTask(@PathVariable Long id,
                            @RequestBody Task task){
        return employeeService.addTask(id,task);
    }
    @DeleteMapping("/employees/id")
    public String deleteEmployee(@PathVariable Long id){
        return employeeService.deleteEmployee(id);
    }
    @PostMapping("/tasks/{id}")
    public Task toggleTaskDone(@PathVariable Long id){
        return taskService.toggleTaskDone(id);
    }

    @DeleteMapping("{employeeid}/tasks/{taskid}")
    public void deleteTask(@PathVariable Long employeeid, @PathVariable Long taskid){
        taskService.deleteTask(employeeid,taskid);
    }

    @PutMapping("/employees/{id}")
    public Employee updateEmployeeInfo(@RequestBody Employee newEmployee,@PathVariable Long id){
        return employeeService.updateEmployeeInfo(newEmployee,id);
    }
    @GetMapping("/registration/confirm")
    public String confirm(@RequestParam("token")String token){
        return employeeService.confirmToken(token);
    }
}

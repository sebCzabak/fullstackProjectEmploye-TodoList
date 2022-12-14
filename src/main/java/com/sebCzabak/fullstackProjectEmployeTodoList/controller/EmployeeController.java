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
     //   this.authenticationManager = authenticationManager;
    }

    private final TaskService taskService;
    private final EmployeeService employeeService;
    private final ConfirmationTokenService confirmationTokenService;
   // private final AuthenticationManager authenticationManager;



    @GetMapping("/employees")
    public List<Employee>getAllEmployees(){
        return employeeService.findALl();
    }
    @GetMapping("/todoItems")
    public List<Task> getAllTasks(){
        return taskService.findAll();
    }
    @GetMapping("/todoItems/{id}")
    public Optional<Task> getTaskById(@PathVariable Long id){
       return taskService.findById(id);
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

    @PostMapping("/tasks/{id}")
    public Task toggleTaskDone(@PathVariable Long id){
        return taskService.toggleTaskDone(id);
    }

    @DeleteMapping("/{employeeId}/tasks/{taskId}")
    public void deleteTodo(@PathVariable Long employeeId, @PathVariable Long taskId){
        taskService.deleteTodo(employeeId,taskId);
    }
    @DeleteMapping("/tasks/deleteTask/{id}")
    public void deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
    }
    @PutMapping("/employees/{id}")
    public Employee updateEmployeeInfo(@RequestBody RegistrationRequest newEmployee,@PathVariable Long id){
        return employeeService.updateEmployeeInfo(newEmployee,id);
    }
    @GetMapping("/registration/confirm")
    public String confirm(@RequestParam("token")String token){
        return employeeService.confirmToken(token);
    }

//    @GetMapping("/employees/Login")
//    public ResponseEntity<?> login (@RequestBody LoginRequest request){
//        try{
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            request.getEmail(),request.getPassword()
//                    )
//            );
//            Employee employee =(Employee) authentication.getPrincipal();
//            employee.setPassword(null);
//            return ResponseEntity.ok()
//                    .header(
//                            HttpHeaders.AUTHORIZATION
//                    );
//        }
    }



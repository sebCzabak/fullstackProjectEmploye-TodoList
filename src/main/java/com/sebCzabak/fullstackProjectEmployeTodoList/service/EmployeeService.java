package com.sebCzabak.fullstackProjectEmployeTodoList.service;


import com.sebCzabak.fullstackProjectEmployeTodoList.exception.EmailAlreadyTakenException;
import com.sebCzabak.fullstackProjectEmployeTodoList.exception.TokenNotFoundException;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Task.Task;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Task.TaskRepo;
import com.sebCzabak.fullstackProjectEmployeTodoList.token.ConfirmationToken.ConfirmationToken;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Employee.Employee;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Employee.EmployeeRepo;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Employee.EmployeeRole;
import com.sebCzabak.fullstackProjectEmployeTodoList.exception.UserNotFoundException;
import com.sebCzabak.fullstackProjectEmployeTodoList.token.ConfirmationToken.ConfirmationTokenService;
import com.sebCzabak.fullstackProjectEmployeTodoList.token.EmailValidator;
import com.sebCzabak.fullstackProjectEmployeTodoList.request.RegistrationRequest;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService implements UserDetailsService {
    private static final String USER_NOT_FOUND = "user with email %s not found";

    public EmployeeService(EmployeeRepo employeeRepo, EmailValidator emailValidator, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationTokenService confirmationTokenService,
                           TaskRepo taskRepo) {
        this.employeeRepo = employeeRepo;
        this.emailValidator = emailValidator;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;

        this.taskRepo = taskRepo;
    }

    private final EmployeeRepo employeeRepo;
    private final EmailValidator emailValidator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final TaskRepo taskRepo;


    public Employee findById(Long id) {
        return employeeRepo.findById(id).orElseThrow(()->new UserNotFoundException(id));
    }

    public void addTask(Long id, Task task){

            Employee employee = employeeRepo.findById(id).orElseThrow(()->new UserNotFoundException(id));

            task.setDescription(task.getDescription());
            employee.getTaskList().add(task);
            taskRepo.save(task);
            employeeRepo.save(employee);

      }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return employeeRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(
                String.format(USER_NOT_FOUND,email)
        ));
    }

    public String register(RegistrationRequest request) {
        boolean isEmailValid = emailValidator.test(request.getEmail());
        if(!isEmailValid){
            throw new IllegalStateException("email not valid");
        }

        return singUpUser(
                new Employee(
                        request.getFullName(),
                        request.getUserName(),
                        request.getEmail(),
                        request.getPassword(),
                        request.getTaskList(),
                        EmployeeRole.USER
                )
        );
    }
    @Transactional
    public String singUpUser(Employee employee){
    boolean employeeExists = employeeRepo
            .findByEmail(employee.getEmail())
            .isPresent();
    if(employeeExists){
        throw new EmailAlreadyTakenException(employee.getEmail());
    }
    String encodePassword =bCryptPasswordEncoder
            .encode(employee.getPassword());
    employee.setPassword(encodePassword);
    employeeRepo.save(employee);
    String token = UUID.randomUUID().toString();
    ConfirmationToken confirmationToken = new ConfirmationToken(
            token,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            employee
    );
    confirmationTokenService.saveConfirmationToken(confirmationToken);
    return token;
    }

    public void deleteEmployee(Long id) {
       boolean employeeExists= employeeRepo.existsById(id);
       if(!employeeExists){
           throw new UserNotFoundException(id);
        }
        employeeRepo.deleteById(id);

    }


    public List<Employee> findALl() {
        return employeeRepo.findAll();
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(()->
                        new TokenNotFoundException(token));
        if(confirmationToken.getConfirmedAt()!=null){
            throw new IllegalStateException("email already confirmed");
        }
        LocalDateTime expiredAt= confirmationToken.getExpiresAt();
        if(expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("token expired");
        }
        confirmationTokenService.setConfirmedAt(token);
        enableEmployee(confirmationToken.getEmployee().getEmail());
        return "confirmed";
    }
    public int enableEmployee(String email){
        return employeeRepo.enableEmployee(email);
    }


    public Employee updateEmployeeInfo(RegistrationRequest newEmployee, Long id) {
        return employeeRepo.findById(id)
                .map(employee -> {
                    employee.setFullName(newEmployee.getFullName());
                    employee.setUserName(newEmployee.getUserName());
                    employee.setEmail(newEmployee.getEmail());
                    employee.setPassword(newEmployee.getPassword());
                    return employeeRepo.save(employee);
                }).orElseThrow(()->new UserNotFoundException(id));
    }

    public Optional<Employee> findByEmailAndPassword(String email, String password) {
        return employeeRepo.findByEmail(email);

    }
}

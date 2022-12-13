package com.sebCzabak.fullstackProjectEmployeTodoList.model.service;

import com.sebCzabak.fullstackProjectEmployeTodoList.model.Task.Task;
import com.sebCzabak.fullstackProjectEmployeTodoList.token.ConfirmationToken.ConfirmationToken;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Employee.Employee;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Employee.EmployeeRepo;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Employee.EmployeeRole;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.exception.UserNotFoundException;
import com.sebCzabak.fullstackProjectEmployeTodoList.token.ConfirmationToken.ConfirmationTokenService;
import com.sebCzabak.fullstackProjectEmployeTodoList.token.EmailValidator;
import com.sebCzabak.fullstackProjectEmployeTodoList.token.RegistrationRequest;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService implements UserDetailsService {
    private static final String USER_NOT_FOUND = "user with email %s not found";

    public EmployeeService(EmployeeRepo employeeRepo, EmailValidator emailValidator, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationTokenService confirmationTokenService) {
        this.employeeRepo = employeeRepo;
        this.emailValidator = emailValidator;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
    }

    private final EmployeeRepo employeeRepo;
    private final EmailValidator emailValidator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    public Employee findById(Long id) {
        return employeeRepo.findById(id).orElseThrow(()->new UserNotFoundException(id));
    }
    public Employee addTask(Long id, Task task){
      employeeRepo.findById(id).orElseThrow(()->new UserNotFoundException(id));

          Employee employee =new Employee();
          employee.getTaskList().add(task);
            return employee;
      }




    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return employeeRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(
                String.format(USER_NOT_FOUND,email)
        ));
    }

    public String register(RegistrationRequest request) {
        boolean isEmailValid = emailValidator.test(request.email());
        if(!isEmailValid){
            throw new IllegalStateException("email not valid");
        }

        return singUpUser(
                new Employee(
                        request.fullName(),
                        request.userName(),
                        request.email(),
                        request.password(),
                        request.taskList(),
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
        throw new IllegalStateException("email already taken");
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
       Employee employee= employeeRepo.findById(id).orElseThrow(()->new UserNotFoundException(id));
       employeeRepo.delete(employee);
    }
}

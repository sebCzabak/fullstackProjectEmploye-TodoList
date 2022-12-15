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
    //    this.emailSender = emailSender;
    }

    private final EmployeeRepo employeeRepo;
    private final EmailValidator emailValidator;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final TaskRepo taskRepo;
  //  private final EmailSender emailSender;


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
    public String register(RegistrationRequest request) {
        boolean isEmailValid = emailValidator.test(request.getEmail());
        if (!isEmailValid) {
            throw new IllegalStateException("email not valid");
        }

        String token = singUpUser(
                new Employee(
                        request.getFullName(),
                        request.getUserName(),
                        request.getEmail(),
                        request.getPassword(),
                        request.getTaskList(),
                        EmployeeRole.USER

        ));
        String link = "http://localhost:8080/api/registration/confirm?token=" + token;
      //  emailSender.send(request.getEmail(),buildEmail(request.getFullName(),link ));
        return token;
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
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return employeeRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(
                String.format(USER_NOT_FOUND,email)
        ));
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
    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}

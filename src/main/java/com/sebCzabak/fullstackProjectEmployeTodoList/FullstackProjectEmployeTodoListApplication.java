package com.sebCzabak.fullstackProjectEmployeTodoList;

import com.sebCzabak.fullstackProjectEmployeTodoList.model.Employee.Employee;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Employee.EmployeeRepo;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Employee.EmployeeRole;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Task.Task;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Task.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.sebCzabak.fullstackProjectEmployeTodoList.model.Employee.EmployeeRole.USER;

@SpringBootApplication
public class FullstackProjectEmployeTodoListApplication implements CommandLineRunner{

	public FullstackProjectEmployeTodoListApplication(EmployeeRepo employeeRepo, TaskRepo taskRepo) {
		this.employeeRepo = employeeRepo;
		this.taskRepo = taskRepo;
	}

	private final EmployeeRepo employeeRepo;
	private final TaskRepo taskRepo;
	public static void main(String[] args) {
		SpringApplication.run(FullstackProjectEmployeTodoListApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Employee employee = new Employee();
		employee.setFullName("JohnDoe");
		employee.setUserName("jd");
		employee.setPassword("123");
		employee.setEmail("john@email.com");
		employee.setEmployeeRole(USER);
		Task task =new Task();
		task.setDescription("Test");

		employee.getTaskList().add(task);
		taskRepo.save(task);
		employeeRepo.save(employee);

	}
}

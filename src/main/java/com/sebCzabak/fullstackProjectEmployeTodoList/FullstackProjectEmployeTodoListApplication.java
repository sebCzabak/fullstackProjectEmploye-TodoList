package com.sebCzabak.fullstackProjectEmployeTodoList;


import com.sebCzabak.fullstackProjectEmployeTodoList.model.Employee.Employee;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Employee.EmployeeRepo;

import com.sebCzabak.fullstackProjectEmployeTodoList.model.Employee.EmployeeRole;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Task.Task;
import com.sebCzabak.fullstackProjectEmployeTodoList.model.Task.TaskRepo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.hibernate.cfg.AvailableSettings.USER;

@SpringBootApplication
public class FullstackProjectEmployeTodoListApplication implements CommandLineRunner {

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
		Employee employee =new Employee();
		employee.setFullName("John Rambo");
		employee.setUserName("rocky");
		employee.setEmail("rocky@gmail.com");
		employee.setPassword("1234");
		employee.setEmployeeRole(EmployeeRole.USER);
		employee.setLocked(false);

		Task task =new Task();
		task.setDescription("Get the Tittle");

		employee.getTaskList().add(task);
		taskRepo.save(task);
		employeeRepo.save(employee);

	}
}


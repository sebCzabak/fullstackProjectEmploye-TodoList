package com.sebCzabak.fullstackProjectEmployeTodoList;

import com.sebCzabak.fullstackProjectEmployeTodoList.model.Employee.EmployeeRepo;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase
@Rollback(value = false)
public class EmployeeRepositoryTests {

    private TestEntityManager testEntityManager;
    private EmployeeRepo employeeRepo;
}

package com.sebCzabak.fullstackProjectEmployeTodoList.model.Task;

import com.sebCzabak.fullstackProjectEmployeTodoList.model.Employee.Employee;
import jakarta.persistence.*;

@Entity
public class Task {

    @Id
    @SequenceGenerator(
            name="task_sequence",
            sequenceName = "task_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_sequence"
    )
    private Long id;
    private String description;
    private Boolean done = false;


    public Task() {
    }

    public Task(String description, Boolean done) {

        this.description = description;
        this.done = done;


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }


}

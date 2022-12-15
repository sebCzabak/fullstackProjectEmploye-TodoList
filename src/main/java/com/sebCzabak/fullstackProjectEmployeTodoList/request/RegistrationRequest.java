package com.sebCzabak.fullstackProjectEmployeTodoList.request;

import com.sebCzabak.fullstackProjectEmployeTodoList.model.Task.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegistrationRequest {

    private String fullName;
    private String userName;
    private  String email;
    private String password;
    private List<Task>taskList= new ArrayList<>();

    public RegistrationRequest(String fullName, String userName, String email, String password,List<Task>taskList) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.taskList =taskList;
    }

    @Override   public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationRequest that = (RegistrationRequest) o;
        return Objects.equals(fullName, that.fullName) && Objects.equals(userName, that.userName) && Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, userName, email, password);
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public String toString() {
        return "RegistrationRequest{" +
                "fullName='" + fullName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public RegistrationRequest() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

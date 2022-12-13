package com.sebCzabak.fullstackProjectEmployeTodoList.request;

import com.sebCzabak.fullstackProjectEmployeTodoList.model.Task.Task;

import java.util.List;
import java.util.Objects;

public record RegistrationRequest(String fullName, String userName, String email, String password,List<Task>taskList) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationRequest that = (RegistrationRequest) o;
        return Objects.equals(fullName, that.fullName) && Objects.equals(userName, that.userName) && Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, userName, email, password);
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


}

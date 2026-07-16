package com.lam.javarestapi.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lam.javarestapi.util.PhoneNumber;
import com.lam.javarestapi.util.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserRequestDTO implements Serializable {
    @NotBlank(message = "firtName not be must blank")
    private String firstName;
    @NotNull(message = "lastName must be not null")
    private String lastName;
    @PhoneNumber
    private String phone;
    @Email(message = "email invalid")
    private String email;
    @NotNull(message = "birthDate must be not null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date birthDate;
    private UserStatus status;
    @NotEmpty
    List<String> permission;

    public UserRequestDTO(String firstName, String lastName, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;

    }

    public List<String> getPermission() {
        return permission;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}

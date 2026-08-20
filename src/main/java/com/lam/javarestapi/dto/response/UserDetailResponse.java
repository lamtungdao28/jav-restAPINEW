package com.lam.javarestapi.dto.response;

import com.lam.javarestapi.util.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class UserDetailResponse implements Serializable {

    private String firstName;

    private String lastName;
    
    private String email;

    private String phone;
}

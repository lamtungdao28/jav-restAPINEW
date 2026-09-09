package com.lam.javarestapi.dto.request;

import com.lam.javarestapi.util.Platform;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SignInRequest implements Serializable {
    @NotBlank(message = "username must be not null")
    private String username;

    @NotBlank(message = "password must be not null")
    private String password;

    @NotNull(message = "must be not null")
    private Platform platform;

    private String deviceToken;

    private String version;

}

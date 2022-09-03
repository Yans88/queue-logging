package com.yansen.dtos.requests;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class RegisterRequest implements Serializable {

    @NotEmpty(message = "username is required")
    @Size(min = 5, message = "username should have at least 5 characters")
    private String username;

    @NotEmpty(message = "email is required")
    @Email(message = "email invalid format")
    private String email;

    @NotEmpty(message = "password is required")
    @Size(min = 8, message = "password should have at least 8 characters")
    private String password;
}

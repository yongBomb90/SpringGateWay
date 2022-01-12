package com.example.userservice.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestLogin {

    @NotNull(message = "Email Not Null")
    @Size(min = 2, message = "Email size 2")
    @Email
    private String email;

    @NotNull(message = "Password Not Null")
    @Size(min = 8, message = "Password size 8")
    private String password;



}

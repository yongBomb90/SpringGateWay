package com.example.userservice.vo;


import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestUser {

    @NotNull(message = "Email Not Null")
    @Size(min = 2, message = "Email size 2")
    @Email
    private String email;

    @NotNull(message = "name Not Null")
    @Size(min = 2, message = "name size 2")
    private String name;

    @NotNull(message = "password Not Null")
    @Size(min = 8, message = "password size 8")
    private String pwd;
}

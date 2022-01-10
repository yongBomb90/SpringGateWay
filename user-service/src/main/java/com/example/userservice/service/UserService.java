package com.example.userservice.service;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.jpa.UserEntity;

public interface UserService {

    UserDTO createUser(UserDTO param);

    UserDTO getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll();

}

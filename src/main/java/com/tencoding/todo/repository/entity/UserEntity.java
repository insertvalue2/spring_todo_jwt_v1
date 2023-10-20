package com.tencoding.todo.repository.entity;

import lombok.Data;

@Data
public class UserEntity {
    private int userId;
    private String username;
    private String email;
    //private String password;  
}

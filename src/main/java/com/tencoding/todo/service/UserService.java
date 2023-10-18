package com.tencoding.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tencoding.todo.dto.UserDTO;
import com.tencoding.todo.repository.mapper.UserMapper;
import com.tencoding.todo.repository.model.User;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    
    public void register(UserDTO userDTO) {
        userMapper.register(userDTO);
    }

    
    public User login(String email, String password) {
        return userMapper.login(email, password);
    }
}
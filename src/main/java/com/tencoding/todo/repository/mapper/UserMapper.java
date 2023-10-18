package com.tencoding.todo.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tencoding.todo.dto.UserDTO;
import com.tencoding.todo.repository.model.User;

@Mapper
public interface UserMapper {
    void register(UserDTO userDTO);
    User login(@Param("email") String email, @Param("password") String password);
}
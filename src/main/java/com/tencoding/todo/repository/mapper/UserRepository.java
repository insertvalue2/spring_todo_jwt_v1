package com.tencoding.todo.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tencoding.todo.dto.UserDTO;
import com.tencoding.todo.repository.entity.UserEntity;

@Mapper
public interface UserRepository {
	
	void singUp(UserDTO userDTO);
	UserEntity signin(@Param("email") String email, @Param("password") String password);

}
package com.tencoding.todo.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tencoding.todo.dto.UserDTO;
import com.tencoding.todo.repository.entity.UserEntity;

@Mapper
public interface UserRepository {
	// 사용자가 보낸 데이터 집합 - UserDTO
	void singUp(UserDTO userDTO);
	// DB 결과 집합 모델링 - UserEntity
	UserEntity signin(@Param("email") String email, @Param("password") String password);

}
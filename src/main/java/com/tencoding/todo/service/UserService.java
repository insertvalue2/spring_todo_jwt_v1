package com.tencoding.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tencoding.todo.dto.UserDTO;
import com.tencoding.todo.repository.entity.UserEntity;
import com.tencoding.todo.repository.mapper.UserRepository;

@Service
public class UserService {
	
	//추후 final 변경 예정  
	@Autowired
    private UserRepository userRepository;
	
	public void singUp(UserDTO userDTO) {
		userRepository.singUp(userDTO);
	}
	
	public UserEntity singin(String email, String password) {
		return userRepository.signin(email, password);
	}
}


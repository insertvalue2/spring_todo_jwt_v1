package com.tencoding.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tencoding.todo.dto.UserDTO;
import com.tencoding.todo.repository.model.User;
import com.tencoding.todo.service.UserService;
import com.tencoding.todo.utils.JwtUtil;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
	@Autowired
    private UserService userService;
	@Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")         // HTTP 메세지 body 
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        userService.register(userDTO);
        return new ResponseEntity<>("회원가입 성공", HttpStatus.CREATED);
    }

	/*
	@PostMapping("/login") public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
	  
	  User user = userService.login(userDTO.getEmail(), userDTO.getPassword());
	  if (user != null) {
	  	return new ResponseEntity<>(user, HttpStatus.OK);
	  } else {
	  	return new ResponseEntity<>("로그인 실패", HttpStatus.UNAUTHORIZED);
	   }
	 }
	 */
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        User user = userService.login(userDTO.getEmail(), userDTO.getPassword());
        if (user != null) {
            // If login is successful, generate JWT token
            String token = jwtUtil.generateToken(userDTO.getEmail());
            return new ResponseEntity<>(token, HttpStatus.OK);  // Return the token
        } else {
            return new ResponseEntity<>("로그인 실패", HttpStatus.UNAUTHORIZED);
        }
    }
    
}
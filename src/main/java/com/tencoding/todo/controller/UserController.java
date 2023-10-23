package com.tencoding.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tencoding.todo.dto.UserDTO;
import com.tencoding.todo.repository.entity.UserEntity;
import com.tencoding.todo.service.UserService;
import com.tencoding.todo.utils.JwtUtil;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
	@Autowired
    private UserService userService;
	@Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/sign-up")         // HTTP 메세지 body 
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        userService.singUp(userDTO);
        return new ResponseEntity<>("회원가입 성공", HttpStatus.CREATED);
    }

    
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody UserDTO userDTO) {
        UserEntity user = userService.singin(userDTO.getEmail(), userDTO.getPassword());
        if (user != null) {
        	// JWT 토큰 생성 
            String token = jwtUtil.generateToken(userDTO.getEmail());
            return new ResponseEntity<>(token, HttpStatus.OK);  // Return the token
        } else {
            return new ResponseEntity<>("로그인 실패", HttpStatus.UNAUTHORIZED);
        }
    }
    
}
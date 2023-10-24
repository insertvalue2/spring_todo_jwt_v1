package com.tencoding.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

	@PostMapping("/sign-up") // HTTP 메세지 body
	public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
		userService.singUp(userDTO);
		return new ResponseEntity<>("회원가입 성공", HttpStatus.CREATED);
	}

	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody UserDTO userDTO) {
	    UserEntity user = userService.singin(userDTO.getEmail(), userDTO.getPassword());
	    
	    if (user != null) {
	        // JWT 토큰 생성 
	        String token = jwtUtil.generateToken(userDTO.getEmail(), user.getUserId());

	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Authorization", "Bearer " + token); // 헤더에 JWT 토큰 추가

	        return ResponseEntity.ok().headers(headers).body(user);
	    } else {
	        return new ResponseEntity<>("로그인 실패", HttpStatus.UNAUTHORIZED);
	    }
	}


}
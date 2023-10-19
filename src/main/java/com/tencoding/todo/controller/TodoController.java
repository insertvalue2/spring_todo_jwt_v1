package com.tencoding.todo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todos")
public class TodoController {

	@GetMapping({"/", ""})
	public String todoList() {
		return "list...";
	}
}

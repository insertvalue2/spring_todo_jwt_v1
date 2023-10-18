package com.tencoding.todo.apiConttroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

	@GetMapping("/todos")
	public String todoList() {
		return "list...";
	}
}

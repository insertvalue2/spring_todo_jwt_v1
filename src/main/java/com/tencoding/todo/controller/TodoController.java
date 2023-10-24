package com.tencoding.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tencoding.todo.dto.TodoDTO;
import com.tencoding.todo.repository.entity.TodoEntity;
import com.tencoding.todo.service.TodoService;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

/*
 * 일반적인 네이밍 방식
 * 1. CRUD(Create, Read, Update, Delete) 기반 네이밍
 * 2. 동사 + 명사 형태:
 * GET /todos: 모든 할 일 항목을 가져오는 메서드 - listTodos 
 * POST /todos: 새로운 할 일 항목을 생성하는 메서드 - createTodo
 * */
@Slf4j
@RestController
@RequestMapping("/todos")
public class TodoController {
	
	// final 로 선언하는 이유 
	// 불변성 보장: 한번 초기화 되면 상태 변경 불가능  
	// 스레드 안전성: final 필드는 스레드 간 공유될 때 안전
	// 의도 표현: 한번 초기화 되면 불변성을 나타낸다.
	// 즉, 가독성과 안정성 측면에서 도움이 될 수 있습니다
	// TODO - UserController 로 final 변경 해야 함
    private final TodoService todoService;

    // final 키워드가 필드에 사용되면 해당 필드는 반드시 
	// 생성자를 통해 초기화한다.
	@Autowired // 명시적으로 습관화 - DI (@Autowired 명시 안해도 됨) 
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TodoEntity>> getTodoList() {
        List<TodoEntity> todos = todoService.readAllTodo();
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTodoById(@PathVariable Integer id) {
        TodoEntity todo = todoService.readTodoById(id);
        if (todo != null) {
            return new ResponseEntity<>(todo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("null", HttpStatus.OK);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> postTodo(@RequestBody TodoDTO todoDto) {
    	int result = todoService.createTodo(todoDto);
    	return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> putTodoById(@PathVariable Integer id, @RequestBody TodoDTO todoDTO) {
        // 유효성 검사 - 생략.. 
    	log.error("req Todo :  {}, ", todoDTO.toString());
        int result = todoService.updateTodoById(id, todoDTO);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTodoById(@PathVariable Integer id) {
        // 유효성 검사 - 생략.. 
        int result = todoService.deleteTodoById(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}


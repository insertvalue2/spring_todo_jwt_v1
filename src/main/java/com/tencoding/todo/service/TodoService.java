package com.tencoding.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tencoding.todo.dto.TodoDTO;
import com.tencoding.todo.repository.entity.TodoEntity;
import com.tencoding.todo.repository.mapper.TodoRepository;

@Service
public class TodoService {
	
	// final 로 선언하는 이유 
	// 불변성 보장: 한번 초기화 되면 상태 변경 불가능  
	// 스레드 안전성: final 필드는 스레드 간 공유될 때 안전
	// 의도 표현: 한번 초기화 되면 불변성을 나타낸다.
	private final TodoRepository todoRepository;
	
	// final 키워드가 필드에 사용되면 해당 필드는 반드시 
	// 생성자를 통해 초기화한다.
	@Autowired // 명시적으로 습관화 - DI 
	public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }
	

	public List<TodoEntity> readAllTodo() {
		return todoRepository.findAllTodos();
	}

	public void createTodo(TodoDTO todoDto) {
		todoRepository.createTodo(todoDto);
	}

	public TodoEntity readTodoById(Integer todoId) {
		return todoRepository.findByIdTodo(todoId);
	}

	public int updateTodoById(Integer todoId, TodoDTO todoDTO) {
		return todoRepository.updateById(todoDTO);
	}

	public void deleteTodoById(Integer id) { 
		todoRepository.deleteById(id);
	}

}

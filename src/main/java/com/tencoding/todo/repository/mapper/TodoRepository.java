package com.tencoding.todo.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tencoding.todo.dto.TodoDTO;
import com.tencoding.todo.repository.entity.TodoEntity;

@Mapper
public interface TodoRepository {
	
	public List<TodoEntity> findAllTodos(); 
	public int createTodo(TodoDTO todoDto);
	public TodoEntity findByIdTodo(Integer todoId);
	public int updateById(TodoDTO todoDTO);
	public void deleteById(Integer id);
}

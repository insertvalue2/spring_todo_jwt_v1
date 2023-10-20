package com.tencoding.todo.dto;

import lombok.Data;

@Data
public class TodoDTO {
	
	private Long id;
    private String title;
    private boolean completed;
}


package com.tencoding.todo.dto;

import lombok.Data;

@Data
public class TodoDTO {
	
    private String title;
    private boolean completed;
    private int userId;
}


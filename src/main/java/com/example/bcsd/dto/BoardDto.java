package com.example.bcsd.dto;

import com.example.bcsd.model.Board;

public class BoardDto {
    private Long id;
    private String title;

    public BoardDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public BoardDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}

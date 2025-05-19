package com.example.bcsd.dto;

public class ArticleUpdateRequestDto {
    private Long boardId;
    private String title;
    private String content;

    public ArticleUpdateRequestDto(Long boardId, String title, String content) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
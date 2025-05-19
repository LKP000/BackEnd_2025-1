package com.example.bcsd.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ArticleCreateRequestDto {
    @JsonProperty("board_id")
    private Long boardId;
    @JsonProperty("author_id")
    private Long authorId;
    private String title;
    private String content;

    public ArticleCreateRequestDto(Long boardId, Long authorId, String title, String content) {
        this.boardId = boardId;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
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
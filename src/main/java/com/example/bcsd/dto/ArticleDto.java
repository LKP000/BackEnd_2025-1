package com.example.bcsd.dto;

import com.example.bcsd.model.Article;

import java.time.LocalDate;

public class ArticleDto {
    private Long id;
    private Long boardId;
    private Long memberId;
    private String title;
    private String content;
    private String createdDate;
    private String modifiedDate;

    public ArticleDto(Long id, Long boardId, Long memberId, String title, String content, String createdDate, String modifiedDate) {
        this.id = id;
        this.boardId = boardId;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public ArticleDto(Long id, Long boardId, Long memberId, String title, String content) {
        this.id = id;
        this.boardId = boardId;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
    }

    public ArticleDto(Long boardId, Long memberId, String title, String content) {
        this.boardId = boardId;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
    }

    public ArticleDto(Article article) {
        String modifiedDate = article.getModifiedDate() != null ? article.getModifiedDate().toString() : LocalDate.now().toString();
        String createdDate = article.getCreatedDate() != null ? article.getCreatedDate().toString() : LocalDate.now().toString();
        this.id = article.getId();
        this.boardId = article.getBoard();
        this.memberId = article.getMember();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public ArticleDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}

package com.example.bcsd.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "board_id", nullable = false)
    private Long board;
    @Column(name = "author_id", nullable = false)
    private Long member;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public Article() {}

    public Article(Long id, Long board, Long member,
                   String title, String content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.board = board;
        this.member = member;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public Article(Long id, Long board, Long member, String title, String content) {
        this.id = id;
        this.board = board;
        this.member = member;
        this.title = title;
        this.content = content;
    }

    public Article(Long board, Long member, String title, String content) {
        this.board = board;
        this.member = member;
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBoard() { return board; }

    public void setBoard(Long board) { this.board = board; }

    public Long getMember() { return member; }

    public void setMember(Long member) { this.member = member; }

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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
package com.example.bcsd.dto;

import java.util.List;

public class BoardDto {
    private String title;
    private List<ArticleDto> articles;

    public BoardDto(String title, List<ArticleDto> articles) {
        this.title = title;
        this.articles = articles;
    }

    public String getTitle() {
        return title;
    }

    public List<ArticleDto> getArticles() {
        return articles;
    }
}

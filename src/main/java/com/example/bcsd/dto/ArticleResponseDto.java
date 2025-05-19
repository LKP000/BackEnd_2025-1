package com.example.bcsd.dto;

import java.time.LocalDate;

import com.example.bcsd.model.Article;

public record ArticleResponseDto(
        Long id, Long boardId, Long memberId,
        String title, String content, String created_date, String modified_date)
{
    public static ArticleResponseDto from(Article article) {
        String createdDate = (article.getCreatedDate() != null)? article.getCreatedDate().toString() : LocalDate.now().toString();
        String modifiedDate = (article.getmodifiedDate() != null)? article.getmodifiedDate().toString() : LocalDate.now().toString();

        return new ArticleResponseDto(
                article.getId(), article.getBoard().getId(), article.getMember().getId(),
                article.getTitle(), article.getContent(), createdDate, modifiedDate
        );
    }
}
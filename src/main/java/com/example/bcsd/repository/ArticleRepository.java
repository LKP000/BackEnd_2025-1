package com.example.bcsd.repository;

import com.example.bcsd.model.Article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findById(Long id);
    List<Article> findAll();
    List<Article> findByBoardId(Long boardId);
    List<Article> findByMemberId(Long memberId);
    Article save(Article article);
    void deleteById(Long id);
}
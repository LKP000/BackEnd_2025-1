package com.example.bcsd.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.bcsd.model.Article;

import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepository {
    @PersistenceContext
    private EntityManager em;

    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(em.find(Article.class, id));
    }

    public List<Article> findAll() {
        String jpql = "SELECT a FROM Article a";
        return em.createQuery(jpql, Article.class).getResultList();
    }

    public List<Article> findByBoardId(Long boardId) {
        String jpql = "SELECT a FROM Article a WHERE a.board.id = :boardId";
        return em.createQuery(jpql, Article.class).setParameter("boardId", boardId).getResultList();
    }
    
    public List<Article> findByMemberId(Long memberId) {
        String jpql = "SELECT a FROM Article a WHERE a.member.id = :memberId";
        return em.createQuery(jpql, Article.class).setParameter("memberId", memberId).getResultList();
    }

    @Transactional
    public void save(Article article) {
        if (article.getId() != null) {
            em.merge(article);
        } else {
            em.persist(article);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        Article article = em.find(Article.class, id);
        if (article != null) {
            em.remove(article);
        }
    }
}
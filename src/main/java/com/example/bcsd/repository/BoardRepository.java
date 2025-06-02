package com.example.bcsd.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.example.bcsd.model.Board;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepository {
    @PersistenceContext
    private EntityManager em;

    public Optional<Board> findById(Long id) {
        Board board = em.find(Board.class, id);
        return Optional.ofNullable(board);
    }

    public List<Board> findAll() {
        String jpql = "SELECT b FROM Board b";
        return em.createQuery(jpql, Board.class).getResultList();
    }

    @Transactional
    public void save(Board board) {
        if (board.getId() != null) {
            em.merge(board);
        } else {
            em.persist(board);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        Board board = em.find(Board.class, id);
        if (board != null) {
            em.remove(board);
        }
    }
}
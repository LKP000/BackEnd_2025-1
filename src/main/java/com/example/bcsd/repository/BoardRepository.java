package com.example.bcsd.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bcsd.model.Board;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findById(Long id);
    List<Board> findAll();
    Board save(Board board);
    void deleteById(Long id);
}
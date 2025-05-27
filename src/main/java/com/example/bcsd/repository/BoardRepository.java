package com.example.bcsd.repository;

import com.example.bcsd.dto.ArticleDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.bcsd.model.Board;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepository {
    private final JdbcTemplate jdbcTemplate;

    public BoardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Board> findById(Long id) {
        String sql = "SELECT id, name FROM board WHERE id = ?";
        try {
            Board board = jdbcTemplate.queryForObject(sql, boardRowMapper(), id);
            return Optional.ofNullable(board);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Board> findAll() {
        String sql = "SELECT id, name FROM board";
        return jdbcTemplate.query(sql, boardRowMapper());
    }

    @Transactional
    public void save(Board board) {
        if (board.getId() != null) {
            String sql = "UPDATE board SET name = ? WHERE id = ?";
            jdbcTemplate.update(sql, board.getTitle(), board.getId());
        } else {
            String sql = "INSERT INTO board (name) VALUES (?)";
            jdbcTemplate.update(sql, board.getTitle());
        }
    }

    @Transactional
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM board WHERE id = ?", id);
    }

    private RowMapper<Board> boardRowMapper() {
        return (rs, rowNum) -> new Board(
                rs.getLong("id"),
                rs.getString("name"));
    }
}
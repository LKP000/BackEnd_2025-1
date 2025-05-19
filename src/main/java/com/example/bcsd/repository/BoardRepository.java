package com.example.bcsd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.bcsd.model.Board;

@Repository
public class BoardRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public BoardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Board> findById(Long id) {
        String sql = "SELECT id, name FROM board WHERE id = ?";
        List<Board> boards = jdbcTemplate.query(sql, boardRowMapper(), id);
        return boards.stream().findFirst();
    }

    public List<Board> findAll() {
        String sql = "SELECT id, name FROM board";
        return jdbcTemplate.query(sql, boardRowMapper());
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM board WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void save(Board board) {
        if (board.getId() == null) {
            String sql = "INSERT INTO board (name) VALUES (?)";
            jdbcTemplate.update(sql, board.getTitle());
        } else {
            String sql = "UPDATE board SET name = ? WHERE id = ?";
            jdbcTemplate.update(sql, board.getTitle(), board.getId());
        }
    }

    private RowMapper<Board> boardRowMapper() {
        return (rs, rowNum) -> new Board(
                rs.getLong("id"),
                rs.getString("name"));
    }

}
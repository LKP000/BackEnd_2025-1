package com.example.bcsd.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.bcsd.model.Article;

@Repository
public class ArticleRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;

    public Optional<Article> findById(Long id) {
        String sql = "SELECT id, board_id, author_id, title, content, created_date, modified_date FROM article WHERE id = ?";
        Article article = jdbcTemplate.queryForObject(sql, articleRowMapper(), id);
        return Optional.ofNullable(article);
    }

    public List<Article> findAll() {
        String sql = "SELECT id, board_id, author_id, title, content, created_date, modified_date FROM article";
        return jdbcTemplate.query(sql, articleRowMapper());
    }

    public List<Article> findByBoardId(Long boardId) {
        String sql = "SELECT id, board_id, author_id, title, content, created_date, modified_date FROM article WHERE board_id = ?";
        return jdbcTemplate.query(sql, articleRowMapper(), boardId);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM article WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void save(Article article) {
        if (article.getId() == null) {
            String sql = "INSERT INTO article (board_id, author_id, title, content, created_date, modified_date) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    article.getBoard().getId(),
                    article.getMember().getId(),
                    article.getTitle(),
                    article.getContent(),
                    LocalDate.now(),
                    LocalDate.now());
        } else {
            String sql = "UPDATE article SET board_id = ?, author_id = ?, title = ?, content = ?, modified_date = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    article.getBoard().getId(),
                    article.getMember().getId(),
                    article.getTitle(),
                    article.getContent(),
                    LocalDate.now(),
                    article.getId()
            );
        }
    }

    private RowMapper<Article> articleRowMapper() {
        return (resultSet, rowNum) -> new Article(
                resultSet.getLong("id"),
                boardRepository.findById(resultSet.getLong("board_id"))
                        .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 존재하지 않습니다.")),
                memberRepository.findById(resultSet.getLong("author_id"))
                        .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다.")),
                resultSet.getString("title"),
                resultSet.getString("content"),
                resultSet.getTimestamp("created_date").toLocalDateTime(),
                resultSet.getTimestamp("modified_date").toLocalDateTime());
    }
}
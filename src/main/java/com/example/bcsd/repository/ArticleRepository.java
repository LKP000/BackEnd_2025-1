package com.example.bcsd.repository;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.bcsd.model.Article;
import com.example.bcsd.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepository {
    private final JdbcTemplate jdbcTemplate;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public ArticleRepository(JdbcTemplate jdbcTemplate, BoardRepository boardRepository, MemberRepository memberRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
    }

    public Optional<Article> findById(Long id) {
        String sql = "SELECT id, board_id, author_id, title, content, created_date, modified_date FROM article WHERE id = ?";
        try {
            Article article = jdbcTemplate.queryForObject(sql, articleRowMapper(), id);
            return Optional.ofNullable(article);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Article> findAll() {
        String sql = "SELECT id, board_id, author_id, title, content, created_date, modified_date FROM article";
        return jdbcTemplate.query(sql, articleRowMapper());
    }

    public List<Article> findByBoardId(Long boardId) {
        String sql = "SELECT id, board_id, author_id, title, content, created_date, modified_date FROM article WHERE board_id = ?";
        return jdbcTemplate.query(sql, articleRowMapper(), boardId);
    }
    
    public List<Article> findByMemberId(Long memberId) {
        String sql = "SELECT id, board_id, author_id, title, content, created_date, modified_date FROM article WHERE author_id = ?";
        return jdbcTemplate.query(sql, articleRowMapper(), memberId);
    }

    @Transactional
    public void save(Article article) {
        if (article.getId() != null) {
            String sql = "UPDATE article SET board_id = ?, author_id = ?, title = ?, content = ?, modified_date = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    article.getBoard().getId(),
                    article.getMember().getId(),
                    article.getTitle(),
                    article.getContent(),
                    LocalDate.now(),
                    article.getId()
            );
        } else {
            String sql = "INSERT INTO article (board_id, author_id, title, content, created_date, modified_date) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    article.getBoard().getId(),
                    article.getMember().getId(),
                    article.getTitle(),
                    article.getContent(),
                    LocalDate.now(),
                    LocalDate.now());
        }
    }

    @Transactional
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM article WHERE id = ?", id);
    }

    private RowMapper<Article> articleRowMapper() {
        return (resultSet, rowNum) -> new Article(
                resultSet.getLong("id"),
                boardRepository.findById(resultSet.getLong("board_id"))
                        .orElseThrow(() -> new NotFoundException("해당 게시판이 존재하지 않습니다.")),
                memberRepository.findById(resultSet.getLong("author_id"))
                        .orElseThrow(() -> new NotFoundException("해당 회원이 존재하지 않습니다.")),
                resultSet.getString("title"),
                resultSet.getString("content"),
                resultSet.getTimestamp("created_date").toLocalDateTime(),
                resultSet.getTimestamp("modified_Date").toLocalDateTime());
    }
}
package com.example.bcsd.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.bcsd.model.Member;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Member> findById(Long id) {
        String sql = "SELECT id, name, email, password FROM member WHERE id = ?";
        try {
            Member member = jdbcTemplate.queryForObject(sql, memberRowMapper(), id);
            return Optional.ofNullable(member);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Member> findAll() {
        String sql = "SELECT id, name, email, password FROM member";
        return jdbcTemplate.query(sql, memberRowMapper());
    }

    @Transactional
    public void save(Member member) {
        if (member.getId() != null) {
            String sql = "UPDATE member SET name = ?, email = ?, password = ? WHERE id = ?";
            jdbcTemplate.update(sql, member.getName(), member.getEmail(), member.getPassword(), member.getId());
        } else {
            String sql = "INSERT INTO member (name, email, password) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, member.getName(), member.getEmail(), member.getPassword());
        }
    }

    @Transactional
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM member WHERE id = ?", id);
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> new Member(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password")
        );
    }
}
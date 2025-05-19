package com.example.bcsd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.bcsd.model.Member;

@Repository
public class MemberRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<Member> findById(Long id) {
        String sql = "SELECT id, name, email, password FROM member WHERE id = ?";
        List<Member> members = jdbcTemplate.query(sql, memberRowMapper(), id);
        return members.stream().findFirst();
    }

    public List<Member> findAll() {
        String sql = "SELECT id, name, email, password FROM member";
        return jdbcTemplate.query(sql, memberRowMapper());
    }

    public void save(Member member) {
        if (member.getId() == null) {
            String sql = "INSERT INTO member (name, email, password) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, member.getName(), member.getEmail(), member.getPassword());
        } else {
            String sql = "UPDATE member SET name = ?, email = ?, password = ? WHERE id = ?";
            jdbcTemplate.update(sql, member.getName(), member.getEmail(), member.getPassword(), member.getId());
        }
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM member WHERE id = ?";
        jdbcTemplate.update(sql, id);
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
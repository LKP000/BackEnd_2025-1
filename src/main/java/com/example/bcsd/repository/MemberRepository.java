package com.example.bcsd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bcsd.model.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(Long id);
    List<Member> findAll();
    Member save(Member member);
    void deleteById(Long id);
    Optional<Member> findByEmail(String email);
}
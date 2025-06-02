package com.example.bcsd.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.bcsd.model.Member;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;

    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    public List<Member> findAll() {
        String jpql = "SELECT m FROM Member m";
        return em.createQuery(jpql, Member.class).getResultList();
    }

    @Transactional
    public void save(Member member) {
        if (member.getId() != null) {
            em.merge(member);
        } else {
            em.persist(member);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        Member member = em.find(Member.class, id);
        if (member != null) {
            em.remove(member);
        }
    }
}
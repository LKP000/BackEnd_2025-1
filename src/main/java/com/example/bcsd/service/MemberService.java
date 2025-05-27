package com.example.bcsd.service;

import com.example.bcsd.dto.MemberDto;
import com.example.bcsd.model.Article;
import com.example.bcsd.model.Member;
import com.example.bcsd.repository.ArticleRepository;
import com.example.bcsd.repository.MemberRepository;
import com.example.bcsd.exception.DeletionNotAllowedException;
import com.example.bcsd.exception.EmailAlreadyExistsException;
import com.example.bcsd.exception.NotFoundException;
import com.example.bcsd.exception.NullRequestException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    public MemberService(MemberRepository memberRepository,
                         ArticleRepository articleRepository) {
        this.memberRepository = memberRepository;
        this.articleRepository = articleRepository;
    }

    public MemberDto getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("해당 회원을 찾을 수 없습니다."));

        return new MemberDto(member.getId(), member.getName(), member.getEmail(), member.getPassword());
    }

    public MemberDto createMember(MemberDto dto) {
        List<Member> members = memberRepository.findAll();
        members.stream().filter(m -> m.getEmail().equals(dto.getEmail()))
                .findFirst().ifPresent(m -> {
                    throw new EmailAlreadyExistsException("이미 존재하는 이메일입니다.");
                });

        if (dto.getEmail() == null || dto.getPassword() == null || dto.getName() == null) {
            throw new NullRequestException("요청 값에 null이 존재합니다.");
        }
        Member member = new Member(dto.getEmail(), dto.getPassword(), dto.getName());
        memberRepository.save(member);

        return new MemberDto(member.getId(), member.getName(), member.getEmail(), member.getPassword());
    }

    public MemberDto updateMember(Long memberId, MemberDto dto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("해당 회원을 찾을 수 없습니다."));

        List<Member> members = memberRepository.findAll();
        members.stream().filter(m -> !m.getId().equals(memberId))
                .forEach(m -> {
                    if (m.getEmail().equals(dto.getEmail())) {
                        throw new EmailAlreadyExistsException("이미 존재하는 이메일입니다.");
                    }
                });
        member.setEmail(dto.getEmail());
        memberRepository.save(member);

        return new MemberDto(member.getId(), member.getName(), member.getEmail(), member.getPassword());
    }

    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("해당 회원이 존재하지 않습니다."));

        List<Article> articles = articleRepository.findByAuthorId(memberId);
        if (!articles.isEmpty()) {
            throw new DeletionNotAllowedException("회원이 작성한 게시글이 존재합니다.");
        }

        memberRepository.deleteById(memberId);
    }

}
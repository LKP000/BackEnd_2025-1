package com.example.bcsd.controller;

import com.example.bcsd.model.Member;
import com.example.bcsd.dto.LoginDto;
import com.example.bcsd.dto.MemberDto;
import com.example.bcsd.repository.MemberRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

@Controller
//@RequestMapping("/auth")
public class AuthController {
    private final MemberRepository memberRepository;

    public AuthController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/signup")
    public String signup(MemberDto req) {
        if (memberRepository.findByEmail(req.getEmail()).isPresent()) {
            return "이미 사용 중인 이메일입니다.";
        }

        Member member = new Member(req.getPassword(), req.getName(), req.getEmail());
        memberRepository.save(member);

        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(LoginDto req, HttpSession session) {
        Optional<Member> optionalMember = memberRepository.findByEmail(req.getEmail());
        if (optionalMember.isEmpty()) {
            return "존재하지 않는 계정입니다.";
        }
        Member member = optionalMember.get();

        if (!member.getPassword().equals(req.getPassword())) {
            return "비밀번호가 다릅니다.";
        }
        session.setAttribute("loginMember", member); // 세션 저장

        return "redirect:/";
    }
}
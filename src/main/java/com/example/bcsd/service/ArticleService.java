
package com.example.bcsd.service;

import com.example.bcsd.dto.ArticleDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bcsd.exception.InvalidReferenceException;
import com.example.bcsd.exception.NotFoundException;
import com.example.bcsd.exception.NullRequestException;
import com.example.bcsd.model.Article;
import com.example.bcsd.repository.ArticleRepository;
import com.example.bcsd.repository.BoardRepository;
import com.example.bcsd.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public ArticleService(ArticleRepository articleRepository,
                          MemberRepository memberRepository,
                          BoardRepository boardRepository) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    public ArticleDto getArticlesById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 게시글이 존재하지 않습니다."));
        return new ArticleDto(article.getId(), article.getTitle());
    }

    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    @Transactional
    public ArticleDto createArticle(Article article) {
        if(article.getBoard().getId() == null || article.getMember().getId() == null
            || article.getTitle() == null || article.getContent() == null) {
            throw new NullRequestException("요청 값에 null이 존재합니다.");
        }

        if (boardRepository.findById(article.getBoard().getId()).isEmpty()){
            throw new InvalidReferenceException("해당 게시판이 존재하지 않습니다.");
        }
        if (memberRepository.findById(article.getMember().getId()).isEmpty()){
            throw new InvalidReferenceException("해당 회원이 존재하지 않습니다.");
        }
        articleRepository.save(article);
        return new ArticleDto(article.getId(), article.getTitle());
    }

    @Transactional
    public ArticleDto updateArticle(Article article) {
        if (boardRepository.findById(article.getBoard().getId()).isEmpty()){
            throw new InvalidReferenceException("해당 게시판이 존재하지 않습니다.");
        }
        if (articleRepository.findById(article.getId()).isEmpty()) {
            throw new InvalidReferenceException("해당 게시글이 존재하지 않습니다.");
        }
        article.setModifiedDate(LocalDateTime.now());
        articleRepository.save(article);
        return new ArticleDto(article.getId(), article.getTitle());
    }

    @Transactional
    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 게시글이 존재하지 않습니다."));
        articleRepository.deleteById(article.getId());
    }
}

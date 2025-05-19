
package com.example.bcsd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.bcsd.dto.ArticleResponseDto;
import com.example.bcsd.model.Article;
import com.example.bcsd.model.Board;
import com.example.bcsd.model.Member;
import com.example.bcsd.repository.ArticleRepository;
import com.example.bcsd.repository.BoardRepository;
import com.example.bcsd.repository.MemberRepository;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BoardRepository boardRepository;

    public ArticleResponseDto getArticle(Long id) {
        Article article = articleRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new IllegalArgumentException("해당 기사가 존재하지 않습니다."));
        return ArticleResponseDto.from(article);
    }

    public List<ArticleResponseDto> getAll() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream()
                .map(ArticleResponseDto::from)
                .collect(Collectors.toList());
    }

    public List<ArticleResponseDto> getByBoardId(Long boardId) {
        List<Article> articles = articleRepository.findByBoardId(boardId);
        return articles.stream()
                .map(ArticleResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public ArticleResponseDto createArticle(Long boardId, Long authorId, String title, String content) {
        Member member = memberRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 존재하지 않습니다."));

        Article article = new Article(board, member, title, content);
        articleRepository.save(article);
        return ArticleResponseDto.from(article);
    }

    @Transactional
    public ArticleResponseDto updateArticle(Long id,Long boardId, String title, String content) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 존재하지 않습니다."));
        Article article = articleRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new IllegalArgumentException("해당 기사가 존재하지 않습니다."));
        article.setBoard(board);
        article.setTitle(title);
        article.setContent(content);
        article.setModifiedDate(LocalDateTime.now());
        articleRepository.save(article);

        return ArticleResponseDto.from(article);
    }

    @Transactional
    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new IllegalArgumentException("해당 기사가 존재하지 않습니다."));
        articleRepository.deleteById(article.getId());
    }
}

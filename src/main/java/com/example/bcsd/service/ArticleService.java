
package com.example.bcsd.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bcsd.exception.InvalidReferenceException;
import com.example.bcsd.exception.NotFoundException;
import com.example.bcsd.exception.NullRequestException;
import com.example.bcsd.dto.ArticleDto;
import com.example.bcsd.model.Article;
import com.example.bcsd.model.Board;
import com.example.bcsd.model.Member;
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

    public ArticleDto getArticleById(Long id) {
        return new ArticleDto(findArticleById(id));
    }

    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    public List<Article> getArticlesByBoardId(Long boardId) {
        return articleRepository.findByBoardId(boardId);
    }

    @Transactional
    public ArticleDto createArticle(Long boardId, Long memberId, String title, String content) {
        if (boardId == null || memberId == null || title == null || content == null) {
            throw new NullRequestException("요청 값에 null이 존재합니다.");
        }
        Board board = findBoardById(boardId);
        Member member = findMemberById(memberId);

        Article article = new Article(board, member, title, content);
        articleRepository.save(article);
        return new ArticleDto(article);
    }

    @Transactional
    public ArticleDto updateArticle(Long id, Long boardId, Long memberId, String title, String content) {
        Board board = findBoardById(boardId);
        Member member = findMemberById(memberId);
        Article article = findArticleById(id);

        article.setBoard(board);
        article.setTitle(title);
        article.setContent(content);
        article.setModifiedDate(LocalDateTime.now());

        articleRepository.save(article);
        return new ArticleDto(article);
    }

    @Transactional
    public void deleteArticle(Long id) {
        Article article = findArticleById(id);
        articleRepository.deleteById(article.getId());
    }

    private Article findArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 게시글이 존재하지 않습니다."));
    }

    private Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new InvalidReferenceException("해당 게시판이 존재하지 않습니다."));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new InvalidReferenceException("해당 회원이 존재하지 않습니다."));
    }
}

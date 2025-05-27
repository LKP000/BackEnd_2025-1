package com.example.bcsd.service;

import com.example.bcsd.dto.BoardDto;
import org.springframework.stereotype.Service;

import com.example.bcsd.exception.DeletionNotAllowedException;
import com.example.bcsd.exception.NotFoundException;
import com.example.bcsd.exception.NullRequestException;
import com.example.bcsd.model.Article;
import com.example.bcsd.model.Board;
import com.example.bcsd.repository.ArticleRepository;
import com.example.bcsd.repository.BoardRepository;

import java.util.List;

@Service
public class BoardService {
    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;

    public BoardService(ArticleRepository articleRepository,
                          BoardRepository boardRepository) {
        this.articleRepository = articleRepository;
        this.boardRepository = boardRepository;
    }

    public BoardDto getBoardByBoardId(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시판입니다."));
        return new BoardDto(board.getTitle(), boardRepository.findArticlesByBoardId(boardId));
    }

    public BoardDto createBoard(BoardDto dto) {
        if (dto.getTitle() == null) {
            throw new NullRequestException("게시판 제목이 없습니다.");
        }
        Board board = new Board((long)0, dto.getTitle());
        boardRepository.save(board);
        return new BoardDto(dto.getTitle(), boardRepository.findArticlesByBoardId(board.getId()));
    }

    public BoardDto updateBoard(Long boardId, BoardDto dto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시판입니다."));
        if (dto.getTitle() == null) {
            throw new NullRequestException("게시판 제목이 없습니다.");
        }
        board.setTitle(dto.getTitle());
        boardRepository.save(board);
        return new BoardDto(board.getTitle(), boardRepository.findArticlesByBoardId(boardId));
    }

    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시판입니다."));

        List<Article> articles = articleRepository.findByBoardId(boardId);
        if (articles != null && !articles.isEmpty()) {
            throw new DeletionNotAllowedException("게시판에 게시글이 존재합니다.");
        }

        boardRepository.deleteById(boardId);
    }
}
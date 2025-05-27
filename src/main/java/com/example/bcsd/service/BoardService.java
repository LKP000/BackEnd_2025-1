package com.example.bcsd.service;

import com.example.bcsd.dto.ArticleDto;
import com.example.bcsd.dto.BoardDto;
import com.example.bcsd.exception.InvalidReferenceException;
import com.example.bcsd.model.Member;
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

    public BoardDto getBoard(Long boardId) {
        Board board = findBoardById(boardId);
        return new BoardDto(board);
    }

    public BoardDto createBoard(BoardDto dto) {
        if (dto.getTitle() == null) {
            throw new NullRequestException("게시판 제목이 존재하지 않습니다.");
        }
        Board board = new Board(dto.getId(), dto.getTitle());
        boardRepository.save(board);
        return new BoardDto(board);
    }

    public BoardDto updateBoard(Long boardId, BoardDto dto) {
        Board board = findBoardById(boardId);
        if (dto.getTitle() == null) {
            throw new NullRequestException("게시판 제목이 존재하지 않습니다.");
        }
        board.setTitle(dto.getTitle());
        boardRepository.save(board);
        return new BoardDto(board);
    }

    public void deleteBoard(Long boardId) {
        Board board = findBoardById(boardId);
        List<Article> articles = articleRepository.findByBoardId(boardId);
        if (!(articles.isEmpty() || articles == null)) {
            throw new DeletionNotAllowedException("게시판에 게시글이 존재합니다.");
        }
        boardRepository.deleteById(boardId);
    }

    private Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException("해당 게시판이 존재하지 않습니다."));
    }
}
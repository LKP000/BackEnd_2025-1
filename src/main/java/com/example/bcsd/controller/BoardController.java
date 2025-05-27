package com.example.bcsd.controller;

import com.example.bcsd.dto.BoardDto;
import com.example.bcsd.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDto> getBoard(@PathVariable(name = "boardId") Long boardId) {
        BoardDto dto = boardService.getBoard(boardId);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<BoardDto> createBoard(@RequestBody BoardDto boardDto) {
        BoardDto dto = boardService.createBoard(boardDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<BoardDto> updateBoard(@PathVariable(name = "boardId") Long boardId, @RequestBody BoardDto requestDto) {
        BoardDto dto = boardService.updateBoard(boardId, requestDto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable(name = "boardId") Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.noContent().build();
    }
}

package com.example.bcsd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bcsd.dto.ArticleCreateRequestDto;
import com.example.bcsd.dto.ArticleUpdateRequestDto;
import com.example.bcsd.dto.ArticleResponseDto;
import com.example.bcsd.service.ArticleService;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> getArticle(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok().body(articleService.getArticle(id));
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponseDto>> getAll() {
        return ResponseEntity.ok().body(articleService.getAll());
    }

    @GetMapping(params = "boardId")
    public ResponseEntity<List<ArticleResponseDto>> getAllByBoardId(@RequestParam Long boardId) {
        return ResponseEntity.ok().body(articleService.getByBoardId(boardId));
    }

    @PostMapping
    public ResponseEntity<ArticleResponseDto> createArticle(@RequestBody ArticleCreateRequestDto requestDto) {
        ArticleResponseDto dto = articleService.createArticle(requestDto.getAuthorId(),requestDto.getBoardId(),requestDto.getTitle(), requestDto.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> updateArticle(@PathVariable(name = "id") Long id, @RequestBody ArticleUpdateRequestDto requestDto) {
        ArticleResponseDto dto = articleService.updateArticle(id,requestDto.getBoardId(), requestDto.getTitle(), requestDto.getContent());
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable(name = "id") Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }
}

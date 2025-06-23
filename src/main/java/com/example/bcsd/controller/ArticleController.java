
package com.example.bcsd.controller;

import com.example.bcsd.dto.ArticleDto;
import com.example.bcsd.model.Article;
import com.example.bcsd.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getArticle(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok().body(articleService.getArticleById(id));
    }

    @GetMapping
    public ResponseEntity<List<Article>> getAll() {
        return ResponseEntity.ok().body(articleService.getAll());
    }

    @GetMapping(params = "boardId")
    public ResponseEntity<List<Article>> getAllByBoardId(@RequestParam Long boardId) {
        return ResponseEntity.ok().body(articleService.getArticlesByBoardId(boardId));
    }

    @PostMapping
    public ResponseEntity<ArticleDto> createArticle(@RequestBody ArticleDto article) {
        ArticleDto dto = articleService.createArticle(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleDto> updateArticle(@PathVariable(name = "id") Long id, @RequestBody ArticleDto article) {
        ArticleDto dto = articleService.updateArticle(id, article);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable(name = "id") Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }
}

package com.example.bcsd;

import com.example.bcsd.dto.Article;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    private final Map<Integer, Article> struct = new HashMap<>();
    private int idCnt = 1;

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable int id) {
        Article article = articles.get(id);
        if(article == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(article);
    }

    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        int newId = idCnt++;
        article.setID(newId);
        articles.put(newId, article);
        return ResponseEntity.ok("Updated");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable int id, @RequestBody Article article) {
        if(!articles.containsKey(id)) return ResponseEntity.notFound().build();

        article.setID(id);
        articles.put(id, article);
        return ResponseEntity.ok("Updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable int id) {
        if(!articles.containsKey(id)){
            return ResponseEntity.notFound().build();
        }
        articles.remove(id);
        return ResponseEntity.ok("Deleted");
    }
}
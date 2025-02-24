package board.article.controller;

import board.article.service.ArticleService;
import board.article.service.response.ArticleResponse;
import board.article.service.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/articles")
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping()
    public PageResponse<ArticleResponse> readAll() {
        throw new RuntimeException("not implement");
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ArticleResponse create() {
        throw new RuntimeException("not implement");
    }

    @PutMapping()
    public ArticleResponse update() {
        throw new RuntimeException("not implement");
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        throw new RuntimeException("not implement");
    }
}

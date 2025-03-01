package board.article.controller;

import board.article.service.ArticleService;
import board.article.service.request.ArticleCreateRequest;
import board.article.service.request.ArticleUpdateRequest;
import board.article.service.response.ArticleResponse;
import board.article.service.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/articles")
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/{articleId}")
    public ArticleResponse read(
            @PathVariable Long articleId
    ) {
        return articleService.read(articleId);
    }

    @GetMapping()
    public PageResponse<ArticleResponse> readAll(
            @RequestParam Long boardId,
            @RequestParam Long page,
            @RequestParam Long pageSize
    ) {
        return articleService.readAll(boardId, page, pageSize);
    }

    @GetMapping("/infinite-scroll")
    public List<ArticleResponse> readAllInfiniteScroll(
            @RequestParam Long boardId,
            @RequestParam Long pageSize,
            @RequestParam(required = false) Long lastArticleId
    ) {
        return articleService.readAllInfiniteScroll(boardId, pageSize, lastArticleId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ArticleResponse create(
            @RequestBody ArticleCreateRequest request
    ) {
        return articleService.create(request);
    }

    @PutMapping("/{articleId}")
    public ArticleResponse update(
            @PathVariable Long articleId,
            @RequestBody ArticleUpdateRequest request
    ) {
        return articleService.update(articleId, request);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @RequestParam Long articleId
    ) {
        articleService.delete(articleId);
    }
}

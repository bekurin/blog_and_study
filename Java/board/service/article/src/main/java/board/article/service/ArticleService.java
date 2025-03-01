package board.article.service;

import board.article.entity.Article;
import board.article.repository.ArticleRepository;
import board.article.service.request.ArticleCreateRequest;
import board.article.service.request.ArticleUpdateRequest;
import board.article.service.response.ArticleResponse;
import board.article.service.response.PageResponse;
import board.article.util.PageLimitCalculator;
import board.common.ResourceNotFoundException;
import board.common.snowflake.Snowflake;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private final Snowflake snowflake = new Snowflake();
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Transactional
    public ArticleResponse create(ArticleCreateRequest request) {
        Article article = request.toArticle();
        Article savedArticle = articleRepository.save(article);
        return new ArticleResponse(savedArticle);
    }

    @Transactional
    public ArticleResponse update(Long articleId, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(ResourceNotFoundException::new);
        Article updatedArticle = article.update(request.getTitle(), request.getContent());
        return new ArticleResponse(updatedArticle);
    }

    public ArticleResponse read(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(ResourceNotFoundException::new);
        return new ArticleResponse(article);
    }

    @Transactional
    public void delete(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    public PageResponse<ArticleResponse> readAll(Long boardId, Long page, Long pageSize) {
        List<ArticleResponse> articleResponses = articleRepository.findAll(boardId, (page - 1) * pageSize, pageSize)
                .stream()
                .map(ArticleResponse::new)
                .toList();
        Long count = articleRepository.count(
                boardId,
                PageLimitCalculator.calculatePageLimit(page, pageSize, 10L)
        );
        return new PageResponse<>(articleResponses, count);
    }

    public List<ArticleResponse> readAllInfiniteScroll(Long boardId, Long pageSize, Long lastArticleId) {
        List<Article> articles = lastArticleId == null ?
                articleRepository.findAllInfiniteScroll(boardId, pageSize) :
                articleRepository.findAllInfiniteScroll(boardId, pageSize, lastArticleId);
        return articles.stream().map(ArticleResponse::new).toList();
    }
}

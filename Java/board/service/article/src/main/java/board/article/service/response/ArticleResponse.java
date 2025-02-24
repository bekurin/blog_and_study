package board.article.service.response;

import board.article.entity.Article;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ArticleResponse {
    private final Long articleId;
    private final String title;
    private final String content;
    private final Long boardId;
    private final Long writerId;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public ArticleResponse(Article article) {
        this.articleId = article.getArticleId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.boardId = article.getBoardId();
        this.writerId = article.getWriterId();
        this.createdAt = article.getCreatedAt();
        this.modifiedAt = article.getModifiedAt();
    }
}


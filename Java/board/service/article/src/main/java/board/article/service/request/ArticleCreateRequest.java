package board.article.service.request;

import board.article.entity.Article;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ArticleCreateRequest {
    private String title;
    private String content;
    private Long writerId;
    private Long boardId;

    public Article toArticle() {
        return Article.create(title, content, boardId, writerId);
    }
}

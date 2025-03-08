package board.comment.service.response;

import board.comment.entity.Comment;
import board.comment.entity.CommentV2;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class CommentResponse {
    private Long commentId;
    private String content;
    private Long parentCommentId;
    private Long articleId;
    private Long writerId;
    private Boolean deleted;
    private String path;
    private LocalDateTime createdAt;

    public CommentResponse(CommentV2 commentV2) {
        this.commentId = commentV2.getCommentId();
        this.content = commentV2.getContent();
        this.parentCommentId = null;
        this.path = commentV2.getCommentPath().getPath();
        this.articleId = commentV2.getArticleId();
        this.writerId = commentV2.getWriterId();
        this.deleted = commentV2.getDeleted();
        this.createdAt = commentV2.getCreatedAt();
    }

    public CommentResponse(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.parentCommentId = comment.getParentCommendId();
        this.articleId = comment.getArticleId();
        this.writerId = comment.getWriterId();
        this.deleted = comment.getDeleted();
        this.createdAt = comment.getCreatedAt();
    }

}

package board.comment.service.request;

import board.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentCreateRequest {
    private Long articleId;
    private String content;
    private Long parentCommentId;
    private Long writerId;

    public Comment toComment() {
        return Comment.create(
                content,
                parentCommentId,
                articleId,
                writerId,
                false
        );
    }
}


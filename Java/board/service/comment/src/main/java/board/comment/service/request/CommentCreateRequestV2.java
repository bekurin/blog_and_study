package board.comment.service.request;

import board.comment.entity.CommentPath;
import board.comment.entity.CommentV2;
import lombok.Getter;

@Getter
public class CommentCreateRequestV2 {
    private Long articleId;
    private String content;
    private String parentPath;
    private Long writerId;

    public CommentV2 toCommentV2(CommentV2 parentComment) {
        CommentPath parentCommentPath = parentComment == null ? new CommentPath("") : parentComment.getCommentPath();
        return new CommentV2(content, articleId, writerId, parentCommentPath, false);
    }
}

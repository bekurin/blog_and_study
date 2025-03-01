package board.comment.entity;

import board.common.snowflake.GenerateId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Table(name = "comment")
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    private Long commentId;
    private String content;
    private Long parentCommendId;
    private Long articleId;
    private Long writerId;
    private Boolean deleted;
    private LocalDateTime createdAt;

    public static Comment create(String content, Long parentCommendId, Long articleId, Long writerId, Boolean deleted) {
        Comment comment = new Comment();
        comment.commentId = GenerateId.nextId();
        comment.content = content;
        comment.parentCommendId = parentCommendId;
        comment.articleId = articleId;
        comment.writerId = writerId;
        comment.deleted = deleted;
        comment.createdAt = LocalDateTime.now();
        return comment;
    }

    public boolean isRootComment() {
        return parentCommendId.longValue() == commentId;
    }

    public Comment delete() {
        deleted = true;
        return this;
    }
}

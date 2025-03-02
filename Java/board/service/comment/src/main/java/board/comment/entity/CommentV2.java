package board.comment.entity;

import board.common.snowflake.GenerateId;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Table(name = "comment_v2")
@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentV2 {
    @Id
    private Long commentId;
    private String content;
    private Long articleId; // shard key
    private Long writerId;
    @Embedded
    private CommentPath commentPath;
    private Boolean deleted;
    private LocalDateTime createdAt;

    public CommentV2(String content, Long articleId, Long writerId, CommentPath commentPath, Boolean deleted) {
        this.commentId = GenerateId.nextId();
        this.content = content;
        this.articleId = articleId;
        this.writerId = writerId;
        this.commentPath = commentPath;
        this.deleted = deleted;
        this.createdAt = LocalDateTime.now();
    }

    public boolean isRoot() {
        return commentPath.isRoot();
    }

    public void delete() {
        deleted = true;
    }
}

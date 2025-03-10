package board.comment.service;

import board.comment.entity.CommentV2;
import board.comment.repository.CommentRepositoryV2;
import board.comment.service.request.CommentCreateRequestV2;
import board.comment.service.response.CommentResponse;
import board.comment.service.response.PageResponse;
import board.comment.util.PageLimitCalculator;
import board.common.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.function.Predicate.not;

@Service
@RequiredArgsConstructor
public class CommentServiceV2 {
    private final CommentRepositoryV2 commentRepository;

    public CommentResponse create(CommentCreateRequestV2 request) {
        CommentV2 parentComment = findParentByParentPath(request.getParentPath());

        CommentV2 commentV2 = request.toCommentV2(parentComment);
        CommentV2 savedComment = commentRepository.save(commentV2);

        return new CommentResponse(savedComment);
    }

    private CommentV2 findParentByParentPath(String parentPath) {
        if (parentPath == null) {
            return null;
        }
        return commentRepository.findByPath(parentPath)
                .filter(not(CommentV2::getDeleted))
                .orElseThrow();
    }

    public CommentResponse read(Long commentId) {
        CommentV2 commentV2 = commentRepository.findById(commentId)
                .orElseThrow(ResourceNotFoundException::new);
        return new CommentResponse(commentV2);
    }

    @Transactional
    public void delete(Long commentId) {
        commentRepository.findById(commentId)
                .filter(not(CommentV2::getDeleted))
                .ifPresent(commentV2 -> {
                    if (hasChildren(commentV2)) {
                        commentV2.delete();
                    } else {
                        delete(commentV2);
                    }
                });
    }

    private boolean hasChildren(CommentV2 comment) {
        return commentRepository.findDescendantTopPath(
                comment.getArticleId(),
                comment.getCommentPath().getPath()
        ).isPresent();
    }

    private void delete(CommentV2 comment) {
        commentRepository.delete(comment);
        if (!comment.isRoot()) {
            commentRepository.findByPath(comment.getCommentPath().getParentPath())
                    .filter(CommentV2::getDeleted)
                    .filter(not(this::hasChildren))
                    .ifPresent(this::delete);
        }
    }

    public PageResponse<CommentResponse> readAll(Long articleId, Long page, Long pageSize) {
        List<CommentResponse> commentResponses = commentRepository.findAll(articleId, (page - 1) * pageSize, pageSize).stream()
                .map(CommentResponse::new)
                .toList();
        Long totalCommentCount = commentRepository.count(articleId, PageLimitCalculator.calculatePageLimit(page, pageSize, 10L));
        return new PageResponse<>(commentResponses, totalCommentCount);
    }
}

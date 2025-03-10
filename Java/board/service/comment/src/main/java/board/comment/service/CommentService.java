package board.comment.service;

import board.comment.entity.Comment;
import board.comment.repository.CommentRepository;
import board.comment.service.request.CommentCreateRequest;
import board.comment.service.response.CommentResponse;
import board.comment.service.response.PageResponse;
import board.comment.util.PageLimitCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.function.Predicate.not;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponse create(CommentCreateRequest request) {
        Comment parent = findParent(request);
        Comment comment = commentRepository.save(request.toComment());
        return new CommentResponse(comment);
    }

    private Comment findParent(CommentCreateRequest request) {
        Long parentCommentId = request.getParentCommentId();
        if (parentCommentId == null) {
            return null;
        }
        return commentRepository.findById(parentCommentId)
                .filter(not(Comment::getDeleted))
                .filter(Comment::isRootComment)
                .orElseThrow();
    }

    public CommentResponse read(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        return new CommentResponse(comment);
    }

    @Transactional
    public void delete(Long commentId) {
        commentRepository.findById(commentId)
                .filter(not(Comment::getDeleted))
                .ifPresent(comment -> {
                    if (hasChildren(comment)) {
                        comment.delete();
                    } else {
                        delete(comment);
                    }
                });
    }

    private boolean hasChildren(Comment comment) {
        return commentRepository.countBy(comment.getArticleId(), comment.getCommentId(), 2L) == 2;
    }

    private void delete(Comment comment) {
        commentRepository.delete(comment);
        if (!comment.isRootComment()) {
            commentRepository.findById(comment.getParentCommendId())
                    .filter(Comment::getDeleted)
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

    public List<CommentResponse> readAll(Long articleId, Long lastParentCommentId, Long lastCommentId, Long limit) {
        List<Comment> comments = lastParentCommentId == null || lastCommentId == null ?
                commentRepository.findAllInfiniteScroll(articleId, limit) :
                commentRepository.findAllInfiniteScroll(articleId, lastParentCommentId, lastCommentId, limit);
        return comments.stream()
                .map(CommentResponse::new)
                .toList();
    }
}

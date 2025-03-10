package board.comment.controller;

import board.comment.service.CommentServiceV2;
import board.comment.service.request.CommentCreateRequestV2;
import board.comment.service.response.CommentResponse;
import board.comment.service.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/comments")
@RequiredArgsConstructor
public class CommentControllerV2 {
    private final CommentServiceV2 commentService;

    @GetMapping("/{commentId}")
    public CommentResponse read(
            @PathVariable("commentId") Long commentId
    ) {
        return commentService.read(commentId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse create(
            @RequestBody CommentCreateRequestV2 request
    ) {
        return commentService.create(request);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long commentId
    ) {
        commentService.delete(commentId);
    }

    @GetMapping()
    public PageResponse<CommentResponse> readAll(
            @RequestParam Long articleId,
            @RequestParam Long page,
            @RequestParam Long pageSize
    ) {
        return commentService.readAll(articleId, page, pageSize);
    }
}

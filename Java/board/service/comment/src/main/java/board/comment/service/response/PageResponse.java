package board.comment.service.response;

import lombok.Data;

import java.util.List;

@Data
public class PageResponse<T> {
    private final List<T> content;
    private final Long totalElements;

    public PageResponse(List<T> content) {
        this.totalElements = (long) content.size();
        this.content = content;
    }

    public PageResponse(List<T> content, Long totalElements) {
        this.content = content;
        this.totalElements = totalElements;
    }
}

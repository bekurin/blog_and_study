package board.article.service.response;

import java.util.List;

public class PageResponse<T> {
    private final List<T> content;
    private final Integer totalElements;

    public PageResponse(List<T> content) {
        this.totalElements = content.size();
        this.content = content;
    }
}

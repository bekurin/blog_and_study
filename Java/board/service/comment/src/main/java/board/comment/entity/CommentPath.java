package board.comment.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 무한 depth 댓글의 Path 계산을 위한 클래스
 */
@Getter
@ToString
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentPath {
    public static class Companion {
        private static final String CHARSET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        private static final int DEPTH_CHUNK_SIZE = 5;
        private static final int MAX_DEPTH = 5;
        // MIN_CHUNK = "00000", MAX_CHUNK = "zzzzz"
        private static final String MIN_CHUNK = String.valueOf(CHARSET.charAt(0)).repeat(DEPTH_CHUNK_SIZE);
        private static final String MAX_CHUNK = String.valueOf(CHARSET.charAt(CHARSET.length() - 1)).repeat(DEPTH_CHUNK_SIZE);
    }

    private String path;

    public CommentPath(String path) {
        if (isDepthOverflowed(path)) {
            throw new IllegalArgumentException("depth overflowed");
        }
        CommentPath commentPath = new CommentPath();
        commentPath.path = path;
    }

    private boolean isDepthOverflowed(String path) {
        return calculateDepth(path) > Companion.MAX_DEPTH;
    }

    private int calculateDepth(String path) {
        return path.length() / Companion.DEPTH_CHUNK_SIZE;
    }

    public int getDepth() {
        return calculateDepth(path);
    }

    public Boolean isRoot() {
        return calculateDepth(path) == 1;
    }

    public String getParentPath() {
        return path.substring(0, path.length() - Companion.DEPTH_CHUNK_SIZE);
    }

    public CommentPath createChildCommentPath(String descendantsTopPath) {
        if (descendantsTopPath == null) {
            return new CommentPath(path + Companion.MIN_CHUNK);
        }
        String childrenTopPath = findChildrenTopPath(descendantsTopPath);
        return new CommentPath(increase(childrenTopPath));
    }

    private String increase(String path) {
        String lastChunk = path.substring(path.length() - Companion.DEPTH_CHUNK_SIZE);
        if (isChunkOverflowed(lastChunk)) {
            throw new IllegalStateException("chunk overflowed");
        }

        int charsetLength = Companion.CHARSET.length();

        int value = 0;
        for (char ch : lastChunk.toCharArray()) {
            value = value * charsetLength + Companion.CHARSET.indexOf(ch);
        }

        value = value + 1;

        String result = "";
        for (int i = 0; i < Companion.DEPTH_CHUNK_SIZE; i++) {
            result = Companion.CHARSET.charAt(value % charsetLength) + result;
            value /= charsetLength;
        }

        return path.substring(0, path.length() - Companion.DEPTH_CHUNK_SIZE) + result;
    }

    private boolean isChunkOverflowed(String lastChunk) {
        return Companion.MAX_CHUNK.equals(lastChunk);
    }

    private String findChildrenTopPath(String descendantsTopPath) {
        return descendantsTopPath.substring(0, (getDepth() + 1) * Companion.DEPTH_CHUNK_SIZE);
    }
}

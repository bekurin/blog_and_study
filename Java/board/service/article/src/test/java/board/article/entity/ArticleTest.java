package board.article.entity;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ArticleTest {
    @Nested
    class 아티클을_수정할_때 {

        private final Article article = new Article();

        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {"", "      "})
        void 제목은_NULL이거나_빈값일_수_없다(String title) {
            // given
            String content = "sample content";

            // when & then
            assertThrows(
                    IllegalArgumentException.class,
                    () -> article.update(title, content)
            );
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {"", "      "})
        void 내용은_NULL이거나_빈값일_수_없다(String content) {
            // givne
            String title = "sample Title";

            // when & then
            assertThrows(
                    IllegalArgumentException.class,
                    () -> article.update(title, content)
            );
        }
    }
}

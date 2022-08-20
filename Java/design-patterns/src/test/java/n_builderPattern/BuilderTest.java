package n_builderPattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BuilderTest {

    @Test
    void createPostOrdinary() {
        Post post = new Post()
                .title("테스트 제목")
                .description("테스트 설명")
                .build();

        Assertions.assertInstanceOf(Post.class, post);
        Assertions.assertEquals(post.getTitle(), "테스트 제목");
        Assertions.assertEquals(post.getDescription(), "테스트 설명");
    }
}

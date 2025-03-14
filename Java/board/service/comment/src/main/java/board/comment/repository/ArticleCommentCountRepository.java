package board.comment.repository;


import board.comment.entity.ArticleCommentCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleCommentCountRepository extends JpaRepository<ArticleCommentCount, Long> {
    /**
     * 간단한 쿼리는 이렇게 native query를 사용하는 것도 좋을 것 같다.
     */
    @Query(
            value = "update article_comment_count set comment_count = comment_count + 1 where article_id = :articleId",
            nativeQuery = true
    )
    @Modifying
    int increase(@Param("articleId") Long articleId);

    @Query(
            value = "update article_comment_count set comment_count = comment_count - 1 where article_id = :articleId",
            nativeQuery = true
    )
    @Modifying
    int decrease(@Param("articleId") Long articleId);
}

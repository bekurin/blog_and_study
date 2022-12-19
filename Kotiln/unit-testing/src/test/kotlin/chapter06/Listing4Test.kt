package chapter06

import chapter06.Listing4.Article
import chapter06.Listing4.Comment
import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

internal class Listing4Test {

    @Test
    fun `adding_a_comment_to_an_article`() {
        //given
        val sut = Article()
        val (text, author, now) = Comment("Comment Text", "John Doe", LocalDateTime.now())


        //when
        sut.addComment(text, author, now)

        //then
        assertEquals(1, sut.comments.size)
        assertEquals(text, sut.comments[0].text)
        assertEquals(author, sut.comments[0].author)
        assertEquals(now, sut.comments[0].createdAt)

    }

    @Test
    fun `adding_a_comment_to_an_article2`() {
        //given
        val sut = Article()
        val (text, author, now) = Comment("Comment Text", "John Doe", LocalDateTime.now())

        //when
        sut.addComment(text, author, now)

        //then
        sut.shouldContainNumberOfComments(1)
            .withComment(text, author, now)
    }

    @Test
    fun `adding_a_comment_to_an_article3`() {
        //given
        val sut = Article()
        val comment = Comment("Comment Text", "John Doe", LocalDateTime.now())

        //when
        sut.addComment(comment.text, comment.author, comment.createdAt)

        //then
        assertContains(sut.comments, comment)
    }
}
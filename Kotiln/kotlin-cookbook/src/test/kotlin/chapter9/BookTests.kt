package chapter9

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.time.LocalDate
import java.time.Month
import kotlin.test.assertEquals

data class Book(
    val isbn: String,
    val title: String,
    val author: String,
    val published: LocalDate
)

class BookTests {

    @Test
    internal fun `test book the hard way`() {
        val book = Book("123124", "코틀린 쿡북", "켄 코우젠", LocalDate.of(2013, Month.SEPTEMBER, 30))
        assertEquals(book.isbn, "123124")
        assertEquals(book.title, "코틀린 쿡북")
        assertEquals(book.author, "켄 코우젠")
        assertEquals(book.published, LocalDate.of(2013, Month.SEPTEMBER, 30))
    }

    @Test
    fun `use Junit5 assertAll`() {
        //given
        val book = Book("123124", "코틀린 쿡북", "켄 코우젠", LocalDate.of(2013, Month.SEPTEMBER, 30))

        //then
        assertAll("check all properties of a book",
            { assertEquals(book.isbn, "123124") },
            { assertEquals(book.title, "코틀린 쿡북") },
            { assertEquals(book.author, "켄 코우젠") },
            { assertEquals(book.published, LocalDate.of(2013, Month.SEPTEMBER, 30)) }
        )
    }

    @Test
    fun `use data class`() {
        //given
        val book = Book("123124", "코틀린 쿡북", "켄 코우젠", LocalDate.of(2013, Month.SEPTEMBER, 30))
        val expectedBook = Book("123124", "코틀린 쿡북", "켄 코우젠", LocalDate.of(2013, Month.SEPTEMBER, 30))

        //then
        assertEquals(book, expectedBook)
    }
}
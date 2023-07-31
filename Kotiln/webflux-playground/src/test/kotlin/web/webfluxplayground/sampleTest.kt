package web.webfluxplayground

import org.junit.jupiter.api.Test
import kotlin.random.Random

class sampleTest {

    val prefix = "insert into user (phone) values"

    @Test
    fun `insert 10000개 만들기`() {
        val insertItems = mutableListOf<String>()

        repeat(10000) {
            val insertItem = "010${Random.nextInt(1000, 9999)}${Random.nextInt(1000, 9999)}"
            insertItems.add(insertItem)
        }

        val query = "$prefix ${insertItems.joinToString {item -> "($item)" }}"
        println(query)
    }
}

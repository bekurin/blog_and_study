package chapter03

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class Customer2Test : IntegrationTests() {

    @Test
    fun `purchase_succeeds_when_enough_inventory`() {
        //given
        database.doSomething()
        //when

        //then
    }
}

open class IntegrationTests(
) {
    lateinit var database: Database

    @BeforeEach
    fun init() {
        database = Database()
    }

    @AfterEach
    fun dispose() {
        database.dispose()
    }
}

class Database {

    init {
        println("database 초기화")
    }

    fun dispose() {
        println("database 삭제")
    }

    fun doSomething() {
        println("doSomething")
    }
}
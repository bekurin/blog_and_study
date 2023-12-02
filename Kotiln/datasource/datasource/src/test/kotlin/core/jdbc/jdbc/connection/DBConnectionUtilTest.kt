package core.jdbc.jdbc.connection

import core.jdbc.connection.DBConnectionConstant.PASSWORD
import core.jdbc.connection.DBConnectionConstant.URL
import core.jdbc.connection.DBConnectionConstant.USERNAME
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.DriverManager

class DBConnectionUtilTest {
    private val log: Logger = LoggerFactory.getLogger(DBConnectionUtilTest::class.java)

    @Test
    fun `DriverManager로 커넥션을 생성하면 매번 새로운 연결을 획득한다`() {
        val conn1 = DriverManager.getConnection(URL, USERNAME, PASSWORD)
        val conn2 = DriverManager.getConnection(URL, USERNAME, PASSWORD)

        log.info("connection={}, class={}", conn1, conn1::class.java)
        log.info("connection={}, class={}", conn2, conn2::class.java)
    }
}

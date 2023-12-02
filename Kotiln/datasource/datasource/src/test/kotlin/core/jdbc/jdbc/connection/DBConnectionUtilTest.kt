package core.jdbc.jdbc.connection

import com.zaxxer.hikari.HikariDataSource
import core.jdbc.connection.DBConnectionConstant.PASSWORD
import core.jdbc.connection.DBConnectionConstant.URL
import core.jdbc.connection.DBConnectionConstant.USERNAME
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.jdbc.datasource.DriverManagerDataSource
import java.sql.Connection
import java.sql.DriverManager

class DBConnectionUtilTest {
    private val log: Logger = LoggerFactory.getLogger(DBConnectionUtilTest::class.java)

    @Test
    fun `DriverManager로 커넥션을 생성하면 매번 새로운 연결을 획득한다`() {
        // 매번 설정 파일을 파라미터로 넘겨줘야한다.
        val conn1 = DriverManager.getConnection(URL, USERNAME, PASSWORD)
        val conn2 = DriverManager.getConnection(URL, USERNAME, PASSWORD)
        loggingConnections(conn1, conn2)
    }

    @Test
    fun `DriverManagerDataSource를 사용하여 DriverManager를 Datasource로 변경할 수 있다`() {
        // 설정과 연결이 분리되어 있다.
        val datasource = DriverManagerDataSource(URL, USERNAME, PASSWORD)

        val conn1 = datasource.connection
        val conn2 = datasource.connection
        loggingConnections(conn1, conn2)
    }

    @Test
    fun `커넥션 풀을 사용하면 기존에 생성된 연결을 가져와서 사용한다`() {
        val givenMaximumPoolSize = 8
        val datasource = HikariDataSource().apply {
            jdbcUrl = URL
            username = USERNAME
            password = PASSWORD
            maximumPoolSize = 8
            connectionTimeout = 0
            poolName = "HANGMAN"
        }

        val connections = (1..givenMaximumPoolSize).map {
            datasource.connection
        }.toTypedArray()

        loggingConnections(*connections)
        Thread.sleep(3000L)
    }

    private fun loggingConnections(vararg connections: Connection) {
        connections.map { connection ->
            log.info("connection={}, class={}", connection, connection::class.java)
        }
    }
}

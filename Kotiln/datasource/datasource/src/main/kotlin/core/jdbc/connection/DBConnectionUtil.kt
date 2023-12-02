package core.jdbc.connection

import core.jdbc.connection.DBConnectionConstant.PASSWORD
import core.jdbc.connection.DBConnectionConstant.URL
import core.jdbc.connection.DBConnectionConstant.USERNAME
import java.sql.Connection
import java.sql.DriverManager

object DBConnectionConstant {
    const val URL: String = "jdbc:mysql://localhost:3306/test"
    const val USERNAME: String = "root"
    const val PASSWORD: String = ""
}

class DBConnectionUtil {
    companion object {
        fun getConnection(): Connection {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD)
        }
    }
}

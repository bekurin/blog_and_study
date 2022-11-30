package core.r2dbch2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class R2dbcH2Application

fun main(args: Array<String>) {
    runApplication<R2dbcH2Application>(*args)
}

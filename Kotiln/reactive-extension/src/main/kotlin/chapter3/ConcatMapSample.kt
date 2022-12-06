package chapter3

import io.reactivex.Flowable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class ConcatMapSample {
    fun main() {
        val flowable = Flowable.just("A", "B", "C")
            .concatMap { data ->
                Flowable.just(data).delay(1000L, TimeUnit.MILLISECONDS)
            }

        flowable.subscribe { data ->
            val threadName = Thread.currentThread().name
            val time: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ss.SSS"))
            println("${threadName}: data=${data}, time=${time}")
        }
        Thread.sleep(4000L)
    }
}

fun main() {
    ConcatMapSample().main()
}
package chapter3

import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class FlatMapSample {
    fun main() {
        val flowable = Flowable.just("A", "B", "C")
            .flatMap { data ->
                Flowable.just(data).delay(1000L, TimeUnit.MILLISECONDS)
            }

        flowable.subscribe { data ->
            val threadName = Thread.currentThread().name
            println("${threadName}: $data")
        }
        Thread.sleep(2000L)
    }
}

fun main() {
    FlatMapSample().main()
}
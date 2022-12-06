package chapter3

import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class SyncSlowerSample {
    fun main() {
        Flowable.interval(1000L, TimeUnit.MILLISECONDS)
            .doOnNext { data ->
                println("emit: ${System.currentTimeMillis()}ms data: $data")
            }
            .subscribe {
                Thread.sleep(2000L)
            }
        Thread.sleep(5000L)
    }
}

fun main() {
    SyncSlowerSample().main()
}
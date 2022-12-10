package chapter4

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class TakeSample {
    fun main() {
        val flowable = Flowable.interval(1000L, TimeUnit.MILLISECONDS)
            .take(3)

        flowable.subscribe(DebugSubscriber())
        Thread.sleep(4000L)
    }
}

/**
 * count 만큼의 값까지만 데이터를 통지한다.
 */
fun main() {
    TakeSample().main()
}
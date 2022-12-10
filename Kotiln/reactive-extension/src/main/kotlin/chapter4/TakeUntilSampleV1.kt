package chapter4

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class TakeUntilSampleV1 {
    fun main() {
        val flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
            .takeUntil { data -> data == 7L }

        flowable.subscribe(DebugSubscriber())
        Thread.sleep(3000L)
    }
}

/**
 * 지정된 조건에 달성할 때까지 데이터를 통지한다.
 */
fun main() {
   TakeUntilSampleV1().main()
}
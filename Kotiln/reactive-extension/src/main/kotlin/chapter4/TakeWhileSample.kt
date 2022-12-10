package chapter4

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class TakeWhileSample {
    fun main() {
        val flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
            .takeWhile { data -> data != 5L }

        flowable.subscribe(DebugSubscriber())
        Thread.sleep(2000L)
    }
}

/**
 * 반복문의 while과 같이 동작한다.
 */
fun main() {
    TakeWhileSample().main()
}
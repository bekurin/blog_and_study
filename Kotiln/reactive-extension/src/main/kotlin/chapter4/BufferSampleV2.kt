package chapter4

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class BufferSampleV2 {
    fun main() {
        val flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
            .take(7)
            .buffer(Flowable.timer(1000L, TimeUnit.MILLISECONDS))

        flowable.subscribe(DebugSubscriber())
        Thread.sleep(4000L)
    }
}

/**
 * 데이터를 통지할 때 리스트로 변환하여 반환한다.
 */
fun main() {
    BufferSampleV2().main()
}
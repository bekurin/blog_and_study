package chapter4

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class BufferSampleV1 {
    fun main() {
        val flowable = Flowable.interval(100L, TimeUnit.MILLISECONDS)
            .take(10)
            .buffer(3)

        flowable.subscribe(DebugSubscriber())
        Thread.sleep(3000L)
    }
}

/**
 * buffer에 정의된 수 만큼 쌓였다가 데이터를 반환한다.
 */
fun main() {
    BufferSampleV1().main()
}
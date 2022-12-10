package chapter4

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class TakeLastSampleV2 {
    fun main() {
        val flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
            .take(10)
            .takeLast(2, 1000L, TimeUnit.MILLISECONDS)

        flowable.subscribe(DebugSubscriber())
        Thread.sleep(4000L)
    }
}

/**
 * count와 함께 시간을 지정한다.
 * 지정된 시간 전부터 통지된 데이터 중 끝에서부터 지정한 조건에 따라 데이터를 통지한다.
 */
fun main() {
    TakeLastSampleV2().main()
}
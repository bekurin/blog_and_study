package chapter4

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class TakeLastSampleV1 {
    fun main() {
        val flowable = Flowable.interval(800L, TimeUnit.MILLISECONDS)
            .take(5)
            .takeLast(2)

        flowable.subscribe(DebugSubscriber())
        Thread.sleep(5000L)
    }
}

/**
 * 통지된 데이터들의 끝에서부터 지정한 조건까지 데이터를 통지한다.
 */
fun main() {
    TakeLastSampleV1().main()
}
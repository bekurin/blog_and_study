package chapter4

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class TakeUntilSampleV2 {
    fun main() {
        val flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
            .takeUntil(Flowable.timer(1000L, TimeUnit.MILLISECONDS))

        flowable.subscribe(DebugSubscriber())
        Thread.sleep(2000L)
    }
}

/**
 * 인자 flowable이 첫번째 결과를 통지하기 전까지 값을 통지한다.
 * 시간으로 종료를 시킬 때 사용가능한 방법?
 */
fun main() {
    TakeUntilSampleV2().main()
}
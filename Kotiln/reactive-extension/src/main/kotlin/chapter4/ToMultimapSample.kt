package chapter4

import chapter4.util.DebugSingleObserver
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class ToMultimapSample {
    fun main() {
        val single = Flowable.interval(500L, TimeUnit.MILLISECONDS)
            .take(5)
            .toMultimap { data: Long ->
                if (data % 2 == 0L) "짝수" else "홀수"
            }

        single.subscribe(DebugSingleObserver())
        Thread.sleep(3000L)
    }
}

/**
 * Flowable이 완료를 통지하는 시점에 결과를 반환한다.
 * toMap과는 다르게 이미 키에 값이 있다면 키에 연결된 collection에 값을 추가한다.
 */
fun main() {
    ToMultimapSample().main()
}
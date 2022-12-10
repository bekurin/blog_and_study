package chapter4

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable

class DistinctUntilChangedSampleV1 {
    fun main() {
        val flowable = Flowable.just("A", "a", "a", "A", "a")
            .distinctUntilChanged()

        flowable.subscribe(DebugSubscriber())
    }
}

/**
 * 같은 값이라도 연속되지만 않는다면 데이터를 통지한다.
 */
fun main() {
    DistinctUntilChangedSampleV1().main()
}
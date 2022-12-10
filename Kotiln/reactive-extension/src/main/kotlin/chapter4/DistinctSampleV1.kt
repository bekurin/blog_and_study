package chapter4

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable

class DistinctSampleV1 {
    fun main() {
        val flowable = Flowable.just("A", "B", "a", "b", "A", "a", "b", "B")
            .distinct()

        flowable.subscribe(DebugSubscriber())
    }
}

/**
 * 이미 통지된 데이터와 같다면 해당 데이터를 통지하지 않는다.
 */
fun main() {
    DistinctSampleV1().main()
}
package chapter4

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable

class FlatMapSampleV3 {
    fun main() {
        val origin = Flowable.just(1, 2, 0, 4, 5)
            .map { data -> 10 / data }

        val flowable = origin.flatMap(
            { data -> Flowable.just(data) },
            { _ -> Flowable.just(-1) },
            { Flowable.just(100) }
        )

        flowable.subscribe(DebugSubscriber<Int>())
    }
}

/**
 * 예외 발생 시 error에 정의된 함수로 대체할 수 있다.
 */
fun main() {
    FlatMapSampleV3().main()
}
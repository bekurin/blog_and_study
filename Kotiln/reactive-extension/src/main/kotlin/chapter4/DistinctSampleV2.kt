package chapter4

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable

class DistinctSampleV2 {
    fun main() {
        val flowable = Flowable.just("A", "b", "a", "B", "A", "B", "a", "b")
            .distinct { data -> data.lowercase() }

        flowable.subscribe(DebugSubscriber())
    }
}

/**
 * distinct 안에 keySelector를 넣어주면 데이터를 형태를 바꾸어 distinct 함수를 실행한다.
 * 저장되는 데이터는 원본 데이터로 먼저 저장된 원본 데이터를 사용한다.
 */
fun main() {
    DistinctSampleV2().main()
}
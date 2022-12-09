package chapter4

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable

class MapSample {
    fun main() {
        val flowable = Flowable
            .just("A", "B", "C", "D", "E")
            .map { data -> data.lowercase() }

        flowable.subscribe(DebugSubscriber<String>())
    }
}

/**
 * 결과가 null이 될 수 있다면 flatMap을 사용하는 것을 고려해보자
 */
fun main() {
    MapSample().main()
}
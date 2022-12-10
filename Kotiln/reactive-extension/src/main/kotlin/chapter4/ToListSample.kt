package chapter4

import chapter4.util.DebugSingleObserver
import io.reactivex.Flowable

class ToListSample {
    fun main() {
        val single = Flowable.just("A", "B", "C", "D", "E")
            .toList()

        single.subscribe(DebugSingleObserver())
    }
}

/**
 * 통지되어야할 모든 데이터를 담아 반환하는 것으로 buffer의 용량을 초과하면 메모리가 부족해질 수 있다.
 */
fun main() {
    ToListSample().main()
}
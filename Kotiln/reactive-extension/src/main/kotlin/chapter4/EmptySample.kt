package chapter4

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable

class EmptySample {
    fun main() {
        Flowable
            .empty<Int>()
            .subscribe(DebugSubscriber<Int>())
    }
}

/**
 * empty는 바로 OnComplete를 호출한다.
 */
fun main() {
    EmptySample().main()
}
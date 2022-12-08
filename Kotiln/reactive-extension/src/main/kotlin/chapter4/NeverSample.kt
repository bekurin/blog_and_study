package chapter4

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable

class NeverSample {
    fun main() {
        Flowable
            .never<Int>()
            .subscribe(DebugSubscriber<Int>())
    }
}

/**
 * Error와는 다르게 아무것도 수행하지 않는다.
 */
fun main() {
    NeverSample().main()
}
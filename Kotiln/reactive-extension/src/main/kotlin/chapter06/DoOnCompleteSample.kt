package chapter06

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable

class DoOnCompleteSample {
    fun main() {
        Flowable.range(1, 5)
            .doOnComplete { println("doOnComplete!") }
            .subscribe(DebugSubscriber())
    }
}

fun main() {
    DoOnCompleteSample().main()
}
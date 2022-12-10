package chapter4

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable

class DistinctUntilChangedSampleV2 {
    fun main() {
        val flowable = Flowable.just("1", "1.0", "0.1", "0.10", "1")
            .distinctUntilChanged { data1, data2 ->
                val convert1 = data1.toBigDecimal()
                val convert2 = data2.toBigDecimal()
                convert1.compareTo(convert2) == 0
            }

        flowable.subscribe(DebugSubscriber())
    }
}

/**
 * 연속되는 값인지 판단하는 정의하여 넘겨줄 수 있다.
 */
fun main() {
   DistinctUntilChangedSampleV2().main()
}
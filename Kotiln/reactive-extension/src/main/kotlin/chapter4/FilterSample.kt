package chapter4

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class FilterSample {
    fun main() {
        val flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
            .filter { data: Long ->
                data % 2 == 0L
            }

        flowable.subscribe(DebugSubscriber())
        Thread.sleep(3000L)
    }
}

/**
 * filter 의 결과가 true 건에 대해서만 결과 통지
 */
fun main() {
   FilterSample().main()
}
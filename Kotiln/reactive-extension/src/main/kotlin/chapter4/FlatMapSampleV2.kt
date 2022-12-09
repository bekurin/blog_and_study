package chapter4

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class FlatMapSampleV2 {
    fun main() {
        val flowable = Flowable.range(1, 3)
            .flatMap(
                { data: Int ->
                    return@flatMap Flowable.interval(100L, TimeUnit.MILLISECONDS)
                        .take(3)
                },
                { sourceData, newData -> "[${sourceData}] ${newData}" }
            )

        flowable.subscribe(DebugSubscriber<String>())
        Thread.sleep(1000L)
    }
}

/**
 * 원본 Flowable의 정보: sourceData
 * flatMap의 반환된 Flowable: newData
 */
fun main() {
    FlatMapSampleV2().main()
}
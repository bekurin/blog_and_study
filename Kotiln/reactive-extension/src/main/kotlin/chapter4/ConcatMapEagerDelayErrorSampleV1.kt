package chapter4

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit


class ConcatMapEagerDelayErrorSampleV1 {
    fun main() {
        val flowable = Flowable.range(10, 3)
            .concatMapEagerDelayError(
                { sourceData: Int ->
                    Flowable.interval(500L, TimeUnit.MILLISECONDS)
                        .take(3)
                        .doOnNext { data: Long ->
                            if (sourceData == 11 && data == 1L) {
                                throw Exception("예외 발생")
                            }
                        }
                        .map { data: Long -> "[$sourceData] $data" }
                },
                false
            )

        flowable.subscribe(DebugSubscriber())
        Thread.sleep(4000L)
    }
}

/**
 * concatMapEagerDelayError
 * tillTheEnd: True -> 모든 처리가 끝난 후에 Error를 반환한다.
 * tillTheEnd: false -> Error가 발생하면 바로 반환한다.
 */
fun main() {
    ConcatMapEagerDelayErrorSampleV1().main()
}
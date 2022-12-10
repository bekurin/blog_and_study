package chapter4

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class ConcatMapSample {
    fun main() {
        val flowable = Flowable.range(10, 3)
            .concatMap { sourceData ->
                Flowable.interval(500L, TimeUnit.MILLISECONDS)
                    .take(2)
                    .map { data ->
                        val time = System.currentTimeMillis()
                        "${time}ms [${sourceData}] $data"
                    }
            }

        flowable.subscribe(DebugSubscriber())
        Thread.sleep(4000L)
    }
}

/**
 * 500ms마다 데이터를 통지하는 새로운 Flowable을 만들지만 이전 Flowable이 처리되기 전까지는 이후 작업을 하지 않는다.
 */
fun main() {
    ConcatMapSample().main()
}
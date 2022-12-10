package chapter4

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class ConcatMapEagerSample {
    fun main() {
        val flowable = Flowable.range(10, 3)
            .concatMapEager { sourceData ->
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
 * 500ms 마다 데이터를 통지하는 새로운 Flowable을 생성한다.
 * concatMap과는 다르게 처리 자체는 바로 실행된다.
 */
fun main() {
    ConcatMapEagerSample().main()
}
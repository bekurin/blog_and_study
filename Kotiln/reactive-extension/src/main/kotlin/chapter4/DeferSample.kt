package chapter4

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable
import java.time.LocalDateTime

class DeferSample {
    fun main() {
        val flowable = Flowable.defer {
            Flowable.just(LocalDateTime.now())
        }

        flowable.subscribe(DebugSubscriber<LocalDateTime>("No.1"))
        Thread.sleep(2000L)
        flowable.subscribe(DebugSubscriber<LocalDateTime>("No.2"))
        Thread.sleep(2000L)
        flowable.subscribe(DebugSubscriber<LocalDateTime>("No.3"))
        Thread.sleep(2000L)
        flowable.subscribe(DebugSubscriber<LocalDateTime>("No.4"))
    }
}

/**
 * defer는 subscribe가 끝난 후에 다음 subscribe를 실행한다.
 * callback 처럼 추후 Mono, Flux, Maybe 등을 구현할 때 활용할 것 같다.
 */
fun main() {
    DeferSample().main()
}
package chapter06

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable

class DoOnNextSample {
    fun main() {
        Flowable.range(1, 5)
            .doOnNext { data ->
                println("origin data: $data")
            }
            .filter { data ->
                data % 2 == 0
            }
            .doOnNext { data ->
                println("after filter: $data")
            }
            .subscribe(DebugSubscriber())
    }
}

/**
 * doOnNext의 실행 시점에 따라 데이터가 들어올 수도 안 들어올 수도 있다.
 */
fun main() {
    DoOnNextSample().main()
}
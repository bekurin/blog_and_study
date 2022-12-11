package chapter06

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable

class DoOnErrorSample {
    fun main() {
        Flowable.range(1, 5)
            .doOnError { error ->
                println("before map: ${error.message}") }
            .map { data ->
                if (data == 3) {
                    throw Exception("예외 발생")
                } else {
                    data
                }
            }
            .doOnError { error ->
                println("after map: ${error.message}")
            }
            .subscribe(DebugSubscriber())
    }
}

/**
 * 예외 발생 시점에 따라 예외가 onError가 실행되지 않을 수 있다.
 */
fun main() {
    DoOnErrorSample().main()
}
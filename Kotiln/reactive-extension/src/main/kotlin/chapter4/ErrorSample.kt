package chapter4

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable

class ErrorSample<T> {
    fun main() {
        Flowable
            .error<T>(Exception("예외 발생"))
            .subscribe(DebugSubscriber<T>())
    }
}

/**
 * 예외를 발생 시켜 subscriber의 OnError에 넘겨준다.
 */
fun main() {
    ErrorSample<Int>().main()
}
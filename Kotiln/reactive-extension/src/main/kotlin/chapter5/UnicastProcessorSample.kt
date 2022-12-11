package chapter5

import chapter4.util.DebugSubscriber
import io.reactivex.processors.UnicastProcessor

class UnicastProcessorSample {
    fun main() {
        val processor = UnicastProcessor.create<Int>()

        processor.onNext(1)
        processor.onNext(2)
        processor.onNext(3)

        processor.subscribe(DebugSubscriber("No.1"))
        processor.subscribe(DebugSubscriber("--- No.2"))

        processor.onNext(4)
        processor.onNext(5)
        processor.onComplete()
    }
}

/**
 * UnicastProcessor 하나의 subscriber 만 가질 수 있다.
 * 2개 이상의 subscriber 를 연결하면 "This processor allows only a single Subscriber" 예외가 발생한다.
 */
fun main() {
    UnicastProcessorSample().main()
}
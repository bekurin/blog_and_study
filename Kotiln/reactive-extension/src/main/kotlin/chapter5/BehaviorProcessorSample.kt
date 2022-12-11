package chapter5

import chapter4.util.DebugSubscriber
import io.reactivex.processors.BehaviorProcessor

class BehaviorProcessorSample {
    fun main() {
        val processor = BehaviorProcessor.create<Int>()

        processor.subscribe(DebugSubscriber("No.1"))

        processor.onNext(1)
        processor.onNext(2)
        processor.onNext(3)

        processor.subscribe(DebugSubscriber("--- No.2"))

        processor.onNext(4)
        processor.onNext(5)
        processor.onComplete()

        processor.subscribe(DebugSubscriber("------ No.3"))
    }
}

/**
 * BehaviorProcessor는 구독하기 직전의 데이터를 캐시해두었다가 새로운 구독이 시작되는 시점에 캐시 데이터를 전달한다.
 */
fun main() {
   BehaviorProcessorSample().main()
}
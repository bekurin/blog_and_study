package chapter5

import chapter4.util.DebugSubscriber
import io.reactivex.processors.AsyncProcessor

class AsyncProcessorSample {
    fun main() {
        val processor = AsyncProcessor.create<Int>()

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
 * AsyncProcessor는 onComplete가 호출되었을 때 마지막 데이터를 통지한다.
 */
fun main() {
    AsyncProcessorSample().main()
}
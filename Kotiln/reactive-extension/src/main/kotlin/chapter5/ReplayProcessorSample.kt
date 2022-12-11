package chapter5

import chapter4.util.DebugSubscriber
import io.reactivex.processors.ReplayProcessor

class ReplayProcessorSample {
    fun main() {
        val processor = ReplayProcessor.create<Int>()

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
 * ReplayProcessor는 이전 모든 데이터에 대해서 buffer에 저장한다.
 * 새로운 subscriber가 등록될 때마다 buffer에 있는 모든 데이터를 통지한다.
 * 따라서 관리를 제대로 해주지 않으면 메모리 부족 문제가 생길 수 있다.
 */
fun main() {
    ReplayProcessorSample().main()
}
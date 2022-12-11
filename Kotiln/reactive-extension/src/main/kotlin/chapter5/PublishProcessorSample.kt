package chapter5

import chapter4.util.DebugSubscriber
import io.reactivex.processors.PublishProcessor

class PublishProcessorSample {
    fun main() {
        val processor = PublishProcessor.create<Int>()

        processor.subscribe(DebugSubscriber("No.1"))

        processor.onNext(1)
        processor.onNext(2)
        processor.onNext(3)

        processor.subscribe(DebugSubscriber("--- No.2"))

        processor.onNext(4)
        processor.onNext(5)
        processor.onComplete()

        processor.subscribe(DebugSubscriber("------ No.3"))
        processor.onNext(6)
    }
}

/**
 * 여러 subscriber가 processor를 구독할 수 있다.
 * PublishProcessor는 구독 이후의 정보를 전달해준다.
 * 또한, processor가 onComplete를 선언하면 더 이상 subscriber엑게 정보를 주지 않고, 바로 onComplete를 호출한다.
 */
fun main() {
    PublishProcessorSample().main()
}
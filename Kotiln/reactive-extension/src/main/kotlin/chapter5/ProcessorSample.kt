package chapter5

import io.reactivex.processors.PublishProcessor
import org.reactivestreams.Processor
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class ProcessorSample {
    fun main() {
        val processor: Processor<Int, Int> = PublishProcessor.create<Int>()

        processor.subscribe(object : Subscriber<Int?> {
            override fun onSubscribe(subscription: Subscription) {
                subscription.request(Long.MAX_VALUE)
            }

            override fun onNext(data: Int?) {
                println(data)
            }

            override fun onError(error: Throwable) {
                System.err.println("error: $error")
            }

            override fun onComplete() {
                println("complete!")
            }
        })

        processor.onNext(1)
        processor.onNext(2)
        processor.onNext(3)
    }
}

/**
 * Processor 는 (publisher, subscriber)가 동시에 될 수 있다.
 * 현재 기능은 (publisher)의 역할을 보여주고 있다.
 */
fun main() {
    ProcessorSample().main()
}
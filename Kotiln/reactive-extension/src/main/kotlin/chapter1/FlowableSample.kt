package chapter1

import chapter1.FlowableSample.Subscriber
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.FlowableSubscriber
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscription


sealed class FlowableSample {
    class Subscriber : FlowableSubscriber<String> {
        private lateinit var subscription: Subscription

        override fun onSubscribe(subscription: Subscription) {
            this.subscription = subscription
            this.subscription.request(1)
        }

        override fun onNext(item: String?) {
            val threadName = Thread.currentThread().name
            println("${threadName}: $item")
            subscription.request(1)
        }

        override fun onComplete() {
            val threadName = Thread.currentThread().name
            println("${threadName}: Complete!")
        }

        override fun onError(error: Throwable?) {
            error?.printStackTrace()
        }

    }
}

fun main() {
    val flowable = Flowable.create(FlowableOnSubscribe { emitter ->
        val datas = arrayOf("Hello, World!", "안녕, RxJava!")
        for (data in datas) {
            if (emitter.isCancelled) {
                return@FlowableOnSubscribe
            }
            emitter.onNext(data!!)
        }
        emitter.onComplete()
    }, BackpressureStrategy.BUFFER)

    flowable
        .observeOn(Schedulers.computation())
        .subscribe(Subscriber())
    Thread.sleep(500)
}
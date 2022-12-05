package chapter1

import io.reactivex.Flowable
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.concurrent.TimeUnit

class SubscriptionCancelSample {

    class SampleSubscriber : Subscriber<Long> {
        private lateinit var subscription: Subscription
        private var startTime: Long = 0L

        override fun onSubscribe(subscription: Subscription?) {
            this.subscription = subscription!!
            this.startTime = System.currentTimeMillis()
            this.subscription.request(Long.MAX_VALUE)
        }

        override fun onNext(t: Long?) {
            println("t = $t")
            if ((System.currentTimeMillis() - startTime) > 2000) {
                subscription.cancel()
                println("구독 해지!")
                return
            }
        }

        override fun onError(t: Throwable?) {
            t?.printStackTrace()
        }

        override fun onComplete() {
            println("완료!")
        }
    }

    fun main() {
        val flowable = Flowable.interval(200L, TimeUnit.MILLISECONDS)
        flowable.subscribe(SampleSubscriber())

        Thread.sleep(2000L)
    }
}

fun main() {
    SubscriptionCancelSample().main()
}
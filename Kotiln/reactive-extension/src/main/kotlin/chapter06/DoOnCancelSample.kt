package chapter06

import io.reactivex.Flowable
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class DoOnCancelSample {
    fun main() {
        Flowable.interval(100L, TimeUnit.MILLISECONDS)
            .doOnCancel { println("doOnCancel") }
            .subscribe(object : Subscriber<Long> {

                private var startTime by Delegates.notNull<Long>()
                private lateinit var subscription: Subscription

                override fun onSubscribe(subscription: Subscription) {
                    this.startTime = System.currentTimeMillis()
                    this.subscription = subscription
                    this.subscription.request(Long.MAX_VALUE)
                }

                override fun onError(error: Throwable?) {
                    println("error=${error}")
                }

                override fun onComplete() {
                    println("complete!!!")
                }

                override fun onNext(data: Long) {
                    if (System.currentTimeMillis() - startTime > 500L) {
                        println("subscribe canceled!")
                        subscription.cancel()
                        return
                    }
                    println("data = $data")
                }
            })
        Thread.sleep(1500L)
    }
}

fun main() {
    DoOnCancelSample().main()
}
package chapter06

import io.reactivex.Flowable
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class DoOnSubscribeSample {
    fun main() {
        Flowable.range(1, 5)
            .doOnSubscribe {
                println("doOnSubscribe")
            }
            .subscribe(object : Subscriber<Int> {
                override fun onSubscribe(subscription: Subscription) {
                    println("--- Subscriber: onSubscribe")
                    subscription.request(Long.MAX_VALUE)
                }

                override fun onNext(data: Int) {
                    println("--- Subscriber: onNext: $data")
                }

                override fun onComplete() {
                }

                override fun onError(error: Throwable?) {
                }
            })
    }
}

fun main() {
    DoOnSubscribeSample().main()
}
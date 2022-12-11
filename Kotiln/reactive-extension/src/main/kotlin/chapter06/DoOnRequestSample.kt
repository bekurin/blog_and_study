package chapter06

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription


class DoOnRequestSample {
    fun main() {
        Flowable.range(1, 3)
            .doOnRequest { size ->
                println("기존 데이터: size=$size")
            }
            .observeOn(Schedulers.computation())
            .doOnRequest { size ->
                println("--- observeOn 적용 후: size=$size")
            }
            .subscribe(object : Subscriber<Int> {
                private lateinit var subscription: Subscription

                override fun onSubscribe(subscription: Subscription) {
                    this.subscription = subscription
                    this.subscription.request(1)
                }

                override fun onNext(data: Int?) {
                    println(data)
                    subscription.request(1)
                }

                override fun onComplete() {
                    println("완료")
                }

                override fun onError(error: Throwable) {
                    println("에러: $error")
                }
            })
        Thread.sleep(500L)
    }
}

fun main() {
    DoOnRequestSample().main()
}
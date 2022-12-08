package chapter3

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MissingBackpressureFlowableSample {

    fun main() {
        val flowable = Flowable.interval(20L, TimeUnit.MILLISECONDS)
            .doOnNext { value ->
                println("emit= $value")
            }

        flowable
            .observeOn(Schedulers.computation())
            .subscribe(
                { value ->
                    try {
                        println("waiting....")
                        Thread.sleep(1000L)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    println("value= $value")
                },
                { error ->
                    println("error=$error")
                },
                {
                    println("complete!!!")
                },
                { subscription ->
                    subscription.request(Long.MAX_VALUE)
                }
            )
        Thread.sleep(5000L)
    }
}

fun main() {
    MissingBackpressureFlowableSample().main()
}
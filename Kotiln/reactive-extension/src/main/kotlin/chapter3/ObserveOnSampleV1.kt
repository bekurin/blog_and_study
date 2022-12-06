package chapter3

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber
import java.util.concurrent.TimeUnit

class ObserveOnSampleV1ResourceSubscriber : ResourceSubscriber<Long>() {
    override fun onNext(t: Long?) {
        try {
            Thread.sleep(1000L)
        } catch (e: InterruptedException) {
            e.printStackTrace()
            System.exit(-1)
        }
        val threadName = Thread.currentThread().name
        println("threadName: $t")
    }

    override fun onError(t: Throwable?) {
        println("error=$t")
    }

    override fun onComplete() {
        println("complete!")
    }

}

class ObserveOnSampleV1 {
    fun main() {
        val flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
            .onBackpressureDrop()

        flowable
            .observeOn(Schedulers.computation(), false, 2)
            .subscribe(ObserveOnSampleV1ResourceSubscriber())
        Thread.sleep(9000L)
    }
}

fun main() {
    ObserveOnSampleV1().main()
}
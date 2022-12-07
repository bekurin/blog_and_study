package chapter3

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class CounterWithMergeSample {
    fun main() {
        val counter: Counter = Counter()

        val source1 = Flowable.range(1, 10000)
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.computation())

        val source2 = Flowable.range(1, 10000)
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.computation())

        Flowable.merge(source1, source2)
            .subscribe(
                { counter.increment() },
                { error -> println("에러=$error") }
            ) { println("counter.get()=${counter.count}") }

        Thread.sleep(2000L)
    }
}

fun main() {
    CounterWithMergeSample().main()
}
package chapter3

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class Counter {
    var count: Int = 0

    fun increment() {
        count++
    }
}

class CounterSample {
    fun main() {
        val counter: Counter = Counter()

        Flowable.range(1, 10000)
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.computation())
            .subscribe(
                { data -> counter.increment() },
                { error -> println("에러=$error") }
            ) { println("counter.get()=${counter.count}") }

        Flowable.range(1, 10000)
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.computation())
            .subscribe(
                { data -> counter.increment() },
                { error -> println("에러=$error") }
            ) { println("counter.get()=${counter.count}") }

    }
}

fun main() {
    CounterWithMergeSample().main()
}
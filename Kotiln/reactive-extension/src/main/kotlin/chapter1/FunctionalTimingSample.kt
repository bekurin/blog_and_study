package chapter1

import io.reactivex.Flowable

class FunctionalTimingSample {
    fun main() {
        val flowable1 = Flowable.just(System.currentTimeMillis())
        val flowable2 = Flowable.fromCallable { System.currentTimeMillis() }

        flowable1.subscribe { data -> println("flowable1 = $data") }
        flowable2.subscribe { data -> println("flowable2 = $data") }

        Thread.sleep(1000L)

        flowable1.subscribe { data -> println("flowable1 = $data") }
        flowable2.subscribe { data -> println("flowable2 = $data") }
    }
}

fun main() {
    FunctionalTimingSample().main()
}
package chapter1

import io.reactivex.Flowable

class MethodChainSample {
    fun main() {
        val flowable = Flowable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .filter { data -> data % 2 == 0 }
            .map { data -> data * 100 }
        flowable.subscribe { data -> println("data = $data") }
    }
}

fun main() {
    MethodChainSample().main()
}
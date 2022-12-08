package chapter3

import io.reactivex.Flowable

class OnErrorResumeItemSample {
    fun main() {
        Flowable.just(1, 3, 5, 0, 2, 4)
            .map { data -> 100 / data }
            .onErrorReturnItem(0)
            .subscribe(
                { data -> println("data=$data") },
                { error -> println("error=$error") }
            ) { println("complete!") }
    }
}

fun main() {
    OnErrorResumeItemSample().main()
}
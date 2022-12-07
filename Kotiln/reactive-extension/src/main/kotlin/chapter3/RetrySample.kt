package chapter3

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

class RetrySample {
    fun main() {
        val flowable = Flowable.create<Int>({ emitter ->
            println("flowable 처리 시작")
            for (i in 1..3) {
                if (i == 2) throw Exception("예외 발생")
                emitter.onNext(1)
            }
            emitter.onComplete()
            println("flowable 처리 완료")
        }, BackpressureStrategy.BUFFER)
            .doOnSubscribe { subscription ->
                println("flowable: doOnSubscribe")
            }
            .retry(2)

        flowable.subscribe(
            { data -> println("data=$data") },
            { error -> println("error=$error") }
        ) { println("완료!") }
    }
}

fun main() {
    RetrySample().main()
}
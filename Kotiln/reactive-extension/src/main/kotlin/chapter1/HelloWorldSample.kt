package chapter1

import io.reactivex.Flowable

class HelloWorldSample {

    fun main() {
        val flowable = Flowable.just("Hello", "World")
        flowable.subscribe { data -> println("data = $data") }
    }
}

fun main() {
    HelloWorldSample().main()
}
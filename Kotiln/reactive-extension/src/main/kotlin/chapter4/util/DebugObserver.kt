package chapter4.util

import io.reactivex.observers.DisposableObserver

class DebugObserver<T>(
    private val label: String = "",
) : DisposableObserver<T>() {

    override fun onNext(data: T) {
        val threadName = Thread.currentThread().name
        if (label == "") {
            println("$threadName: $data")
        } else {
            println("$threadName: $label: $data")
        }
    }

    override fun onError(throwable: Throwable) {
        val threadName = Thread.currentThread().name
        if (label == "") {
            println("$threadName: error= $throwable")
        } else {
            println("$threadName: $label: error= $throwable")
        }
    }

    override fun onComplete() {
        val threadName = Thread.currentThread().name
        if (label == "") {
            println("$threadName: complete!")
        } else {
            println("$threadName: $label: complete!")
        }
    }
}
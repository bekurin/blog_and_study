package chapter4.util

import io.reactivex.observers.DisposableSingleObserver

class DebugSingleObserver<T>(
    private val label: String = "",
) : DisposableSingleObserver<T>() {
    override fun onSuccess(data: T) {
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

}
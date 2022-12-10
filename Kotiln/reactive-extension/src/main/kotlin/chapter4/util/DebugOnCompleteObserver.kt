package chapter4.util

import io.reactivex.observers.DisposableCompletableObserver

class DebugOnCompleteObserver<T>(
    private val label: String = "",
) : DisposableCompletableObserver() {
    override fun onComplete() {
        val threadName = Thread.currentThread().name
        if (label == "") {
            println("$threadName: complete!")
        } else {
            println("$threadName: $label: complete!")
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
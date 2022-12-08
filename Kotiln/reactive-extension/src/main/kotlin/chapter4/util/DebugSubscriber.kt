package chapter4.util

import io.reactivex.subscribers.DisposableSubscriber

/**
 * 기본으로 사용하는 log용 subscriber이다.
 */
class DebugSubscriber<T>(
    private val label: String = ""
): DisposableSubscriber<T>() {

    override fun onNext(data: T) {
        val threadName = Thread.currentThread().name
        if (label == "") {
            println("${threadName}:${data}")
        } else {
            println("${threadName}:${label}:${data}")
        }
    }

    override fun onError(throwable: Throwable?) {
        val threadName = Thread.currentThread().name
        if (label == "") {
            println("${threadName}:${throwable}")
        } else {
            println("${threadName}:${label}:${throwable}")
        }
    }

    override fun onComplete() {
        val threadName = Thread.currentThread().name
        if (label == "") {
            println("${threadName}:complete!")
        } else {
            println("${threadName}:${label}:complete!")
        }
    }
}
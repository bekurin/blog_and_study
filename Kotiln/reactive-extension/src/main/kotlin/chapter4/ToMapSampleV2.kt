package chapter4

import chapter4.util.DebugSingleObserver
import io.reactivex.Flowable

class ToMapSampleV2 {
    fun main() {
        val single = Flowable.just("1A", "2B", "3C", "4D", "1E")
            .toMap(
                { data -> data.substring(0, 1).toLong() },
                { data -> data.substring(1) }
            )

        single.subscribe(DebugSingleObserver())
    }
}

/**
 * ToMap의 첫번째 인자는 키 생성, 두번째 인자는 값 생성이다.
 * 해당 키가 이미 값을 가지고 있다면 새로운 값으로 새롭게 덮어쓴다.
 */
fun main() {
    ToMapSampleV2().main()
}
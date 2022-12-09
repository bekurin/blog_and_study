package chapter4

import chapter4.util.DebugSubscriber
import io.reactivex.Flowable
import java.util.*


class FlatMapSampleV1 {
    fun main() {
        val flowable = Flowable.just("A", "", "B", "", "C")
            .flatMap { data: String ->
                if (data == "") {
                    return@flatMap Flowable.empty<String>()
                } else {
                    return@flatMap Flowable.just(data.lowercase(Locale.getDefault()))
                }
            }
        flowable.subscribe(DebugSubscriber())
    }
}

/**
 * 조건에 따라 Null 빈 Flowable을 처리할 수 있다.
 * 상속 관계이 있는 부모 객체를 데이터로 받아 자식 객체로 변환하는 것도 가능한다.
 */
fun main() {
    FlatMapSampleV1().main()
}
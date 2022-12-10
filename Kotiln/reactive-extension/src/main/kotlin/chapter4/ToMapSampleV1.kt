package chapter4

import chapter4.util.DebugSingleObserver
import io.reactivex.Flowable

class ToMapSampleV1 {
    fun main() {
        val single = Flowable.just("1A", "2B", "3C", "4D", "1E")
            .toMap { data ->
                data.substring(0, 1).toLong()
            }

        single.subscribe(DebugSingleObserver())
    }
}

/**
 * 데이터로 생성한 키, 값 map을 생성하여 반환한다. 이때, toXXX 함수들은 모두 buffer에 값을 저장하므로 메모리 부족에 대비를 해야한다.
 */
fun main() {
    ToMapSampleV1().main()
}
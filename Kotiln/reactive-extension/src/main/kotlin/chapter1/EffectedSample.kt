package chapter1

import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class EffectedSample {

    private enum class State {
        ADD, MULTIPLY
    }

    private lateinit var calcMethod: State

    fun main() {
        calcMethod = State.ADD

        val flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
            .take(10)
            .scan { sum, data ->
                if (calcMethod.equals(State.ADD))
                    sum + data
                else {
                    sum * data
                }
            }

        flowable.subscribe { data -> println("data = $data") }

        Thread.sleep(1000)
        changeState(State.MULTIPLY)
        Thread.sleep(1000)
        changeState(State.ADD)

        // Thread Sleep이 없으면 subscribe 를 처리하지 못한다. 즉, 처리하는 동안에 충분한 시간이 있어야한다.
        Thread.sleep(2000)
    }

    private fun changeState(state: State) {
        println("Change Calc Method ${calcMethod.name} to ${state.name}")
        calcMethod = state
    }
}

fun main() {
    EffectedSample().main()
}
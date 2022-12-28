package chapter03.question06

import chapter03.square
import chapter03.triple

class Solution {
    fun <A, B, C> higherAndThen(): ((A) -> B) -> ((B) -> C) -> (A) -> C =
        { f -> { g -> { x -> g(f(x)) } } }
}

/**
 * Q: 문제 5를 반대로 적용하는 함수 higherAndThen을 만들어라.
 * n이 10일 경우 예제 5와 반대로 적용하는 함수를 만들면 10 * 10 * 3 = 300으로 계산된다.
 */
fun main() {
    val solution = Solution()
    val higherAndThen = solution.higherAndThen<Int, Int, Int>()(square)(triple)
    println("higherAndThen = ${higherAndThen(10)}")
}
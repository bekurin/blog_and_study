package chapter03.question05

import chapter03.square
import chapter03.triple

class Solution {
    fun <A, B, C> higherCompose(): ((B) -> C) -> ((A) -> B) -> (A) -> C =
        { f -> { g -> { x -> f(g(x)) } } }
}

/**
 * Q: 다형적 compose 함수를 만들어라.
 * 함수의 실행 순서 g(x) -> y, f(y) -> z
 * y는 g(x)의 결과, z는 f(y)의 결과
 */
fun main() {
    val solution = Solution()
    val higherCompose = solution.higherCompose<Int, Int, Int>()(square)(triple)
    println("higherCompose = ${higherCompose(10)}")
}
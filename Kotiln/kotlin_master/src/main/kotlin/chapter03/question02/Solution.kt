package chapter03.question02

class Solution {
    fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C = { x -> f(g(x)) }
}

/**
 * Q: compose 함수를 타입 파라미터를 사용하는 다형적 함수로 만들어라.
 * 타입 파라미터를 여러 개 정의하여 사용할 수 있다.
 */
fun main() {
    val solution = Solution()
    val compose = solution.compose<Double, Double, Double>({ x -> x * x }, { y -> y * 0.1 })
    println("compose = ${compose(5.0)}")
}
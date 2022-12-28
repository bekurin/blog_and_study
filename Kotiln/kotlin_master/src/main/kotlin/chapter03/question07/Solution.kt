package chapter03.question07

class Solution {
    fun <A, B, C> partialA(a: A, f: (A) -> (B) -> C): (B) -> C = f(a)
}

/**
 * Q: 인자를 2개 받는 함수를 만들어라.
 */
fun main() {
    val f = { a: Int -> { b: Double -> a * (1 + b / 100) } }
    val solution = Solution()
    val partialA = solution.partialA(89, f)
    println("partialA = ${partialA(10.0)}")
}
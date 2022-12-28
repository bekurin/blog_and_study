package chapter03.question08

class Solution {
    fun <A, B, C> partialB(b: B, f: (A) -> (B) -> C): (A) -> C =
        { a: A -> f(a)(b) }
}

/**
 * Q: 인자 2개를 받는 함수를 만들어라.
 */
fun main() {
    val f = { a: Int -> { b: Double -> a * (1 + b / 100) } }
    val solution = Solution()
    val partialB = solution.partialB(b = 89.0, f)
    println("partialB = ${partialB(10)}")
}
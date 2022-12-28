package chapter03.question10

class Solution {
    fun <A, B, C> curried(f: (A, B) -> C): (A) -> (B) -> C =
        { a -> { b -> f(a, b) } }
}

/**
 * Q: (A, B)에서 C로 가는 커리 함수를 만들어라.
 */
fun main() {
    val sum: (Int, Int) -> Int = { a, b -> a + b }
    val solution = Solution()
    val curried = solution.curried(sum)
    println("curried = ${curried(3)(5)}")
}
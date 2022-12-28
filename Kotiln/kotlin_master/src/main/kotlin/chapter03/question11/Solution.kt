package chapter03.question11

class Solution {
    fun <A, B, C> swapArgs(f: (A) -> (B) -> C): (B) -> (A) -> C =
        { b -> { a -> f(a)(b) } }
}

/**
 * Q: 커리한 함수의 두 인자를 뒤바꾼 새로운 함수를 반환하는 swapArgs를 만들어라.
 * 두 인자를 뒤바꿔서 얻는 이득에 대해서 이해가 되지 않는다. 일반적인 Curry 함수와 다를 것이 무엇인지?
 */
fun main() {
    val (amount, rate) = Pair(1000.0, 10.0)
    val payment = { amount: Double -> { rate: Double -> amount + amount * (rate / 100) } }
    val solution = Solution()
    val swapArgs = solution.swapArgs(payment)
    println("swapArgs = ${swapArgs(rate)(amount)}")
}
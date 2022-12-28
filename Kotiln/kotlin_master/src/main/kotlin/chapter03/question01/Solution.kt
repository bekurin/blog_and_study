package chapter03.question01

import chapter03.square
import chapter03.triple

class Solution {
    fun compose(f: (Int) -> Int, g: (Int) -> Int): (Int) -> Int = { x -> f(g(x)) }
}

/**
 * Q: Int에서 Int로 가는 함수의 합성을 허용하는 Compose 함수를 작성하라
 * () 안의 값은 인자의 타입이나 개수에 따라 달라진다.
 * -> 뒤의 값은 반환 타입으로 Int, String 등과 함께 함수도 반환할 수 있다.
 */
fun main() {
    val solution = Solution()
    val compose = solution.compose(::triple, ::square)
    println("compose = ${compose(5)}")
}
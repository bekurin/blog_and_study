package chapter03.question04

import chapter03.square
import chapter03.triple

class Solution {
    fun squareOfTriple(): (IntUnaryOp) -> (IntUnaryOp) -> IntUnaryOp =
        { x -> { y -> { z -> x(y(z)) } } }
}

typealias IntUnaryOp = (Int) -> Int

/**
 * Q: 두 함수를 합성하는 함수 값을 만들라.
 * typealias를 사용하면 복잡한 타입 관계를 간단하게 관리할 수 있다.
 * 함수를 인자처럼 넘길 수 있다.
 */
fun main() {
    val solution = Solution()
    val squareOfTriple = solution.squareOfTriple()(square)(triple)
    println("squareOfTriple = ${squareOfTriple(10)}")
}
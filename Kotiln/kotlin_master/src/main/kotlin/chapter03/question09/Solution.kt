package chapter03.question09

class Solution {
    fun <A, B, C, D> curried() =
        { a: A ->
            { b: B ->
                { c: C ->
                    { d: D -> "$a, $b, $c, $d" }
                }
            }
        }
}

/**
 * Q: 인자 4개를 입력 받아 문자열로 반환하는 함수를 만들어라.
 */
fun main() {
    val solution = Solution()
    val result = solution.curried<String, String, String, String>()("a")("B")("c")("D")
    println("result = $result")
}
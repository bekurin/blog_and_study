package chapter03.question03

import chapter03.triple

class Solution {
    fun twoSum(): (Int) -> (Int) -> Int = { a -> { b -> a + b } }
}

/**
 * Q: 두 Int 값을 더하는 함수를 작성하라.
 * 함수를 curring 하면 ()()()()... 과 같이 인자를 받을 수 있다.
 * curring은 사칙연산에는 유리할 수 있어도 객체나 enum을 받아야할 경우에는 혼란이 생길 수 있다고 생각한다.
 */
fun main() {
    val solution = Solution()
    val result = solution.twoSum()(3)(5)
    println("result = $result")
}
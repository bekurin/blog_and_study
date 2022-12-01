package chapter9

@JvmOverloads
tailrec fun fibonacci(n: Int, a: Int = 0, b: Int = 1): Int {
    return when (n) {
        0 -> a
        1 -> b
        else -> fibonacci(n - 1, b, a + b)
    }
}

class FiboTests {

}
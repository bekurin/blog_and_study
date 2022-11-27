package chapter4

import util.printLine
import java.math.BigInteger

class FunctionalProgramming {

    fun sumFold(vararg nums: Int) =
        nums.fold(0) { acc, i ->
            println("acc=$acc, i=$i")
            acc + i
        }

    fun factorialFold(n: Long) =
        when (n) {
            0L, 1L -> BigInteger.ONE
            else -> (2..n).fold(BigInteger.ONE) { acc, i ->
                acc * BigInteger.valueOf(i)
            }
        }

    fun fiboFold(n: Int) =
        (2 until n).fold(1 to 1) { (prev, cur), _ ->
            cur to (prev + cur)
        }.second

    fun sumReduce(vararg nums: Int) =
        nums.reduce { acc, i -> acc + i }

    fun sumDoublingReduce(vararg nums: Int) =
        nums.reduce { acc, i ->
            println("acc=$acc, i=$i")
            acc + 2 * i
        }

    @JvmOverloads
    tailrec fun factorialTailrec(n: Long, acc: BigInteger = BigInteger.ONE): BigInteger =
        when (n) {
            0L -> BigInteger.ONE
            1L -> acc
            else -> factorialTailrec(n - 1, acc * BigInteger.valueOf(n))
        }
}

fun main() {
    val chapter4 = FunctionalProgramming()
    println("chapter4.sum(1,2,3,4,5) = ${chapter4.sumFold(1, 2, 3, 4, 5)}")
    printLine()

    println("chapter4.fiboFold(100) = ${chapter4.factorialFold(100)}")
    printLine()

    println("chapter4.fiboFold(5) = ${chapter4.fiboFold(10)}")
    printLine()

    println("chapter4.sumReduce(5) = ${chapter4.sumReduce(1, 2, 3, 4, 5)}")
    printLine()

    println("chapter4.sumDoublingReduce(1,2,3,4,5) = ${chapter4.sumDoublingReduce(1, 2, 3, 4, 5)}")
    printLine()

    println("chapter4.factorialTailrec(5) = ${chapter4.factorialTailrec(5)}")
    printLine()
}

package chapter6

import util.printLine
import kotlin.math.ceil
import kotlin.math.sqrt


fun bad(): Int {
    return (100 until 200).map { it * 2 }
        .filter { it % 3 == 0 }
        .first()
}

fun better(): Int {
    return (100 until 200).map { it * 2 }
        .first { it % 3 == 0 }
}

fun good(): Int {
    return (100 until 2_000_000).asSequence()
        .map { println("doubling $it"); it * 2 }
        .filter { println("filtering $it"); it % 3 == 0 }
        .first()
}

fun Int.isPrime() =
    this == 2 || (2..ceil(sqrt(this.toDouble())).toInt())
        .none {divisor -> this % divisor == 0}

fun nextPrime(num: Int) =
    generateSequence(num + 1) {it + 1}
        .first(Int::isPrime)

fun firstPrime(count: Int) =
    generateSequence(2, ::nextPrime)
        .take(count)
        .toList()

fun primesLessThanV1(max: Int): List<Int> =
    generateSequence(2) {n -> if (n < max) nextPrime(n) else null }
        .toList()
        .dropLast(1)

fun primesLessThanV2(max: Int): List<Int> =
    generateSequence(2, ::nextPrime)
        .takeWhile { it < max }
        .toList()

fun fibonacciSequence() = sequence {
    var terms = Pair(1, 1)

    while (true) {
        yield(terms.first)
        terms = terms.second to terms.first + terms.second
    }
}

fun main() {
    val bad = bad()
    println("bad = $bad")
    printLine()

    val better = better()
    println("better = $better")
    printLine()

    val good = good()
    println("good = $good")
    printLine()

    val firstPrime = firstPrime(10)
    println("firstPrime = $firstPrime")
    printLine()

    val primesLessThanV1 = primesLessThanV1(100)
    println("primesLessThanV1 = $primesLessThanV1")
    printLine()

    val primesLessThanV2 = primesLessThanV2(100)
    println("primesLessThanV2 = $primesLessThanV2")
    printLine()

    val fibonacciSequence = fibonacciSequence()
        .takeWhile { println("yield element = $it"); it < 100 }
        .toList()
    println("fibonacciSequence = $fibonacciSequence")
}
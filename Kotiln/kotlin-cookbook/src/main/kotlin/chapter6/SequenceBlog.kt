package chapter6

import util.printLine
import kotlin.system.measureTimeMillis


fun primeSequence(count: Int = 20) =
    generateSequence(2, ::nextPrime)
        .take(count)
        .toList()

fun primeCollection(last: Int = 20): List<Int> =
    (2 until last + 1)
        .filter { it.isPrime() }
        .toList()

fun firstCollection(last: Int = 20) =
    (1 until last)
        .map { it * 2 }
        .first { it % 7919 == 0 }

fun firstSequence(last: Int = 20) =
    (1 until last).asSequence()
        .map { it * 2 }
        .first { it % 7919 == 0 }

fun complexStepCollection(numbers: List<Int>) =
    numbers
        .map { it * 1 }
        .filter { it % 2 == 0 }
        .filter { it % 4 == 0 }
        .filter { it % 6 == 0 }
        .map { it + (it / 2 + 1) }
        .filter { it % 7 == 0 }
        .toList()

fun complexStepSequence(numbers: List<Int>) =
    numbers.asSequence()
        .map { it * 1 }
        .filter { it % 2 == 0 }
        .filter { it % 4 == 0 }
        .filter { it % 6 == 0 }
        .map { it + (it / 2 + 1) }
        .filter { it % 7 == 0 }
        .toList()


fun main() {
    val count = 1_000_000
    var sequenceResult: List<Int>
    println(
        "measureTimeMillis { primeSequence(count) } = ${
            measureTimeMillis {
                sequenceResult = primeSequence(count)
            }
        }"
    )
    println(
        "measureTimeMillis { primeCollection(sequenceResult.last()) } = ${
            measureTimeMillis {
                primeCollection(
                    sequenceResult.last()
                )
            }
        }"
    )
    printLine()

    println("measureTimeMillis { firstCollection(count) } = ${measureTimeMillis { firstCollection(count) }}")
    println("measureTimeMillis { firstSequence(count) } = ${measureTimeMillis { firstSequence(count) }}")
    printLine()

    var result = (1..4).asSequence()
        .map { print("M$it "); it * it }
        .filter { print("F$it "); it % 2 == 0 }
        .first()
    println("             >> result = $result")
    result = (1..4)
        .map { print("M$it "); it * it }
        .filter { print("F$it "); it % 2 == 0 }
        .first()
    println(">> result = $result")
    printLine()

    val numbers: List<Int> = (0 until 300_000_000).toList()
//    println("measureTimeMillis { complexStepCollection(numbers) } = ${measureTimeMillis { complexStepCollection(numbers) }}")
    println("measureTimeMillis { complexStepSequence(numbers) } = ${measureTimeMillis { complexStepSequence(numbers) }}")
}
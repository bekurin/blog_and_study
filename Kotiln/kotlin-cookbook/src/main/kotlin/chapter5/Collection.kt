package chapter5

import util.printArray
import util.printLine
import util.printList

data class Golfer(
    val score: Int,
    val first: String,
    val last: String
)

fun main() {
    val strings = arrayOf("this", "is", "an", "array", "of", "string")
    println("strings = ${strings.printArray()}")
    printLine()

    val nullStringArray = arrayOfNulls<String>(7)
    println("nullStringArray = ${nullStringArray.printArray()}")
    printLine()

    val array = Array(5) { i -> (i + 1).toString() }
    println("array = ${array.printArray()}")
    printLine()

    println("strings.indices.toList().printList() = ${strings.indices.toList().printList()}")
    printLine()

    val mutableList = mutableListOf("this", "is", "a", "list", "of", "mutable")
    val notMutableList = mutableList.toList()
    println("mutableList = ${mutableList.printList()}")
    println("notMutableList = ${notMutableList.printList()}")
    println("mutableList === notMutableList = ${mutableList === notMutableList}")
    printLine()

    val keys = 1..10
    val map = keys.associate { it to it.toString().repeat(10) }
    println("map = ${map}")
    printLine()

    val range = 1..10
    val chunked = range.chunked(2)
    println("chunked = ${chunked}")
    printLine()

    val windowed = range.windowed(3, 3, partialWindows = true)
    println("windowed = ${windowed}")
    printLine()

    val golfers = listOf(
        Golfer(70, "Jack", "Nicklaus"),
        Golfer(90, "James", "Waston"),
        Golfer(85, "Tom", "Woods"),
        Golfer(80, "Tiger", "Easton"),
        Golfer(70, "Ty", "Webb"),
        Golfer(65, "Bob", "Eden")
    )
    val sorted = golfers.sortedWith(
        compareBy {
            it.score
            it.last
            it.first
        }
    )
    println("sorted = ${sorted}")
    printLine()
}
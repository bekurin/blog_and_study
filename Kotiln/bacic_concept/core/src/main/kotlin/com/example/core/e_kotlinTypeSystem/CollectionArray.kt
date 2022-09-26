package com.example.core.e_kotlinTypeSystem

import com.example.core.a_kotlinBasic.iterator.printLine

fun <T> copyElements(source: Collection<T>,
                     target: MutableCollection<T>) {
    for (item in source) {
        target.add(item)
    }
}

fun main() {
    val source: Collection<Int> = arrayListOf(1, 2, 3, 4, 5)
    val target: MutableCollection<Int> = arrayListOf(1)

    copyElements(source, target)
    println("target = $target")
    printLine()

    val fiveZeros = IntArray(5)
    val fiveZerosToo = intArrayOf(0, 0, 0, 0, 0)
    val squares = IntArray(5) { i -> (i + 1) * (i + 1) }

    println("fiveZeros = ${fiveZeros.joinToString()}")
    println("fiveZerosToo = ${fiveZerosToo.joinToString()}")
    println("squares = ${squares.joinToString()}")
    printLine()

    squares.forEachIndexed { index, element ->
        println("Argument $index is $element")
    }
}
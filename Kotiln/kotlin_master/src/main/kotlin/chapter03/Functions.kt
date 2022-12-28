package chapter03

import chapter03.question04.IntUnaryOp

fun square(n: Int) = n * n

fun triple(n: Int) = n * 3

val square: IntUnaryOp = { it * it }

val triple: IntUnaryOp = { it * 3 }
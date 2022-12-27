package chapter03.listing1

import org.jetbrains.annotations.TestOnly


fun exercise1(f: (Int) -> Int, g: (Int) -> Int): (Int) -> Int = { data ->
    f(g(data))
}

fun <A, B, C> exercise2(f: (B) -> C, g: (A) -> B): (A) -> C = { data ->
    f(g(data))
}

val exercise3: (Int) -> (Int) -> Int = { a -> { b -> a + b } }

val exercise4V1: ((Int) -> Int) -> ((Int) -> Int) -> (Int) -> Int = { x -> { y -> { z: Int -> x(y(z)) } } }
val exercise4V2 = { x: (Int) -> Int -> { y: (Int) -> Int -> { z: Int -> x(y(z)) } } }

typealias IntUnaryOp = (Int) -> Int

val exercise4V3: (IntUnaryOp) -> (IntUnaryOp) -> IntUnaryOp =
    { x -> { y -> { z -> x(y(z)) } } }

fun <T, U, V> exercise5(): ((U) -> V) -> ((T) -> U) -> (T) -> V = { f -> { g -> { x -> f(g(x)) } } }

fun <A, B, C> exercise7(a: A, f: (A) -> (B) -> C): (B) -> C = f(a)

fun <A, B, C> exercise8(b: B, f: (A) -> (B) -> C): (A) -> C = { a: A -> f(a)(b) }

fun <A, B, C, D> exercise9() =
    { a: A ->
        { b: B ->
            { c: C ->
                { d: D ->
                    "$a, $b, $c, $d"
                }
            }
        }
    }

fun <A, B, C> exercise10(f: (A, B) -> C): (A) -> (B) -> C =
    { a ->
        { b -> f(a, b) }
    }

fun <A, B, C> exercise11(f: (A) -> (B) -> C): (B) -> (A) -> (C) =
    { b -> { a -> f(a)(b) } }


fun main() {
}
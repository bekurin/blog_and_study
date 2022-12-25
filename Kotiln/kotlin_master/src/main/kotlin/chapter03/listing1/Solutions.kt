package chapter03.listing1


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

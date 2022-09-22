package com.example.core.c_classObjectInterface

sealed class SealedExpr {
    class SealedNum(val value: Int): SealedExpr()
    class SealedSum(val left: SealedExpr, val right: SealedExpr): SealedExpr()
}

fun sealedEval(e: SealedExpr): Int =
        when (e) {
            is SealedExpr.SealedNum -> e.value
            is SealedExpr.SealedSum -> sealedEval(e.right) + sealedEval(e.left)
        }

// not sealed class
interface Expr
class Num(val value: Int): Expr
class Sum(val left: Expr, val right: Expr): Expr

fun eval(e: Expr): Int =
        when (e) {
            is Num -> e.value
            is Sum -> eval(e.left) + eval(e.right)
            else ->
                throw IllegalStateException("Unknown expression")
        }


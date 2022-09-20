package com.example.core.a_kotlinBasic.enumAndWhen

import com.example.core.a_kotlinBasic.enumAndWhen.Color.*

fun getMnemonic(color: Color) =
        when (color) {
            RED -> "Richard"
            ORANGE -> "Of"
            YELLOW -> "York"
            GREEN -> "Gave"
            BLUE -> "Battle"
            INDIGO -> "In"
            VIOLET -> "Vain"
        }

fun multipleOptionWhen(color: Color) =
        when(color) {
            RED, BLUE, GREEN -> "rgb"
            else -> "not rgb"
        }

fun optionObject(c1: Color, c2: Color) =
        when(setOf(c1, c2)) {
            setOf(RED, YELLOW) -> ORANGE
            setOf(YELLOW, BLUE) -> GREEN
            setOf(BLUE, VIOLET) -> INDIGO
            else -> throw Exception("Nothing matched with ${c1.name} + ${c2.name}")
        }

fun main() {
    println("getMnemonic(Color.BLUE) = ${getMnemonic(BLUE)}")
    println("multipleOptionWhen(Color.RED) = ${multipleOptionWhen(VIOLET)}")
    println("optionObject(RED, VIOLET) = ${optionObject(RED, YELLOW)}")
}
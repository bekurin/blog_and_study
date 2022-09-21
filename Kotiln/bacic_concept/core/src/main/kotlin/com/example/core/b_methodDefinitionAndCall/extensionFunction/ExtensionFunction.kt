package com.example.core.b_methodDefinitionAndCall

import java.lang.StringBuilder

fun String.lastChar(): Char = this.get(this.length - 1)

fun <T> Collection<T>.joinToString(
        separator: String = ", ",
        prefix: String = "",
        postfix: String = ""
): String {
    val result = StringBuilder(prefix)
    for ((index, element) in this.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}

open class View {
    open fun click() = println("View Click")
}

class Button: View() {
    override fun click() {
        println("Button Click")
    }
}

fun View.showOff() = println("I'm a view!")
fun Button.showOff() = println("I'm a button!")

fun main() {
    println("kotlin".lastChar())

    val list = arrayListOf(1, 2, 3, 4, 5)
    println(list.joinToString(";;;"))

    val view: View = Button()
    view.click()
    view.showOff()

    val button: Button = Button()
    button.click()
    button.showOff()
}
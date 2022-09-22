package com.example.core.c_classObjectInterface

open class RichButton : Clickable {
    fun disable() {}

    open fun animate() {}

    final override fun click() {
        // not editable
        println("I was clicked!")
    }
}

abstract class Animated {
    open fun stopAnimating() = println("stop animating!")
    fun animateTwice() {
        for (index in 0..2)
            println("animate!")
    }

    abstract fun animate()
}
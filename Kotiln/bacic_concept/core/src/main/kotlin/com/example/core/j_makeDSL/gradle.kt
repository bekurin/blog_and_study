package com.example.core.j_makeDSL

import com.example.core.a_kotlinBasic.iterator.printLine

class DependencyHandler {
    fun compile(coordinate: String) {
        println("Added dependency on $coordinate")
    }

    operator fun invoke(
            body: DependencyHandler.() -> Unit) {
        body()
    }
}

fun main() {
    val dependencies = DependencyHandler()
    dependencies.compile("org.jetbrains.kotlin:kotlin-stdlib:1.0.0")
    printLine()

    dependencies {
        compile("org.jetbrains.kotlin:kotlin-stdlib:1.0.0")
    }
}
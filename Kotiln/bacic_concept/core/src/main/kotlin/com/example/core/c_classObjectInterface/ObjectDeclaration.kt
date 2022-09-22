package com.example.core.c_classObjectInterface

import com.example.core.c_classObjectInterface.Human.*
import java.io.File

object CaseInsensitiveFileComparator : Comparator<File> {
    override fun compare(o1: File, o2: File): Int {
        return o1.path.compareTo(o2.path, ignoreCase = true)
    }
}

data class Human(val name: String) {
    object NameComparator : Comparator<Human> {
        override fun compare(o1: Human, o2: Human): Int =
                o1.name.compareTo(o2.name)
    }
}

fun main() {
    val result = CaseInsensitiveFileComparator.compare(
            File("/User"), File("/user")
    )
    println("result = ${result}")

    val fileList = listOf(File("/a"), File("/z"), File("/j"))
    println(fileList.sortedWith(CaseInsensitiveFileComparator))

    println("------------------------------------")
    val humanList = listOf(Human("bob"), Human("hangman"), Human("ace"))
    println(humanList.sortedWith(NameComparator))
}
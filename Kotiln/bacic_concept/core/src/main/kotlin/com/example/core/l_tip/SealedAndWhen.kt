package com.example.core.l_tip

import com.example.core.a_kotlinBasic.iterator.printLine

sealed class Item<out T> {
    val type: String
        get() = javaClass.simpleName
    abstract val content: T

    data class NewNote(override val content: Note) : Item<Note>()
    data class NewPencil(override val content: Pencil) : Item<Pencil>()
}

class Note(
        val id: Int,
        val name: String
)

class Pencil(
        val id: Int,
        val name: String
)

fun mapItem(item: Item<*>) = when(item) {
    is Item.NewNote -> println("id = ${item.content.id}, name = ${item.content.name}, type = ${item.type}")
    is Item.NewPencil -> println("id = ${item.content.id}, name = ${item.content.name}, type = ${item.type}")
}

fun main() {
    val pencil = Pencil(3, "나는 연필")
    val note = Note(4, "나는 공책")

    val newNote = Item.NewNote(note)
    val newPencil = Item.NewPencil(pencil)

    mapItem(newNote)
    printLine()

    mapItem(newPencil)
}
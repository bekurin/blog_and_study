package com.example.core.l_tip

import com.example.core.a_kotlinBasic.iterator.printLine
import com.example.core.l_tip.dsl.village

class Village(
    val name: String,
    val address: Address,
    val npcs: List<Npc>
) {
    override fun toString(): String {
        return "Village(name=$name, address=$address, npcs=$npcs"
    }
}

class Address(
    val street: String,
    val city: String
) {
    override fun toString(): String {
        return "Address(street=$street, city=$city)"
    }
}

enum class Type {
    UNDEFINED, NORMAL, QUEST, MERCHANT
}

class Npc(
    val id: Long,
    val name: String,
    val type: Type
) {
    override fun toString(): String {
        return "Npc(id=$id, name=$name, type=$type)"
    }
}

fun main() {
    val village1 = village {
        name = "기본 마을"
        address {
            street = "기본 거리"
            city = "기본 시티"
        }
        npcs {
            npc {
                id = 1L
                name = "기본 npc 1"
                type = Type.NORMAL
            }
            npc {
                id = 2L
                name = "기본 npc 2"
                type = Type.QUEST
            }
        }
    }

    val village2 = Village(
        name = "기본 마을",
        address = Address(
            street = "기본 거리",
            city = "기본 시티"
        ),
        npcs = listOf(
            Npc(
                id = 1L,
                name = "기본 npc 1",
                type = Type.NORMAL
            ),
            Npc(
                id = 2L,
                name = "기본 npc 2",
                type = Type.QUEST
            )
        )
    )
    println("village1 = $village1")
    printLine()
    println("village2 = $village2")
}

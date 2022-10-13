package com.example.core.l_tip.dsl

class VillageBuilder {
    var name: String = ""
    var address: Address? = null
    var npcList: MutableList<Npc> = mutableListOf()

    fun address(builder: AddressBuilder.() -> Unit) {
        address = AddressBuilder().apply(builder).build()
    }

    fun npcs(builder: NpcListBuilder.() -> Unit) {
        npcList.addAll(NpcListBuilder().apply(builder).build())
    }

    fun build() = Village(
        name,
        address ?: throw Exception("address object is null"),
        npcList
    )
}

class AddressBuilder {
    var street: String = ""
    var city: String = ""

    fun build() = Address(street, city)
}

class NpcBuilder {
    var id: Long = 0L
    var name: String = ""
    var type: Type = Type.UNDEFINED

    fun build() = Npc(id, name, type)
}

class NpcListBuilder {
    val npcList: MutableList<Npc> = mutableListOf()

    fun npc(builder: NpcBuilder.() -> Unit) {
        npcList.add(NpcBuilder().apply(builder).build())
    }

    fun build() = npcList
}

fun village(builder: VillageBuilder.() -> Unit) = VillageBuilder().apply(builder).build()
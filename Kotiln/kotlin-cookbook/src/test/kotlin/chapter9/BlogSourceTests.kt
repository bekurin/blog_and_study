package chapter9

import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals

enum class NpcType {
    NORMAL, QUEST
}

data class Npc(
    val id: Long,
    val name: String,
    val type: NpcType,
)

@TestInstance(Lifecycle.PER_CLASS)
class BlogSourceTests {

    @ParameterizedTest(name = "ValueSource({0})")
    @ValueSource(longs = [1, 2])
    fun `valueSourceTest`(id: Long) {
        val npc = Npc(id, "James", NpcType.NORMAL)

        assertEquals(npc.id, id)
        assertEquals(npc.name, "James")
        assertEquals(npc.type, NpcType.NORMAL)
    }

    @ParameterizedTest(name = "CsvSource({0}, {1}, {2})")
    @CsvSource("1, james, NORMAL", "2, Bob, QUEST")
    fun `csvSourceTest`(id: Long, name: String, type: NpcType) {
        val npc = Npc(id, name, type)

        assertEquals(npc.id, id)
        assertEquals(npc.name, name)
        assertEquals(npc.type, type)
    }

    @ParameterizedTest(name = "MethodSource({0}, {1}, {2})")
    @MethodSource("getNpcList")
    fun `methodSourceTest`(dataNpc: Npc) {
        val npc = Npc(dataNpc.id, dataNpc.name, dataNpc.type)

        assertEquals(npc, dataNpc)
    }

    private fun getNpcList(): List<Npc> {
        return listOf(
            Npc(1, "james", NpcType.NORMAL),
            Npc(2, "Bob", NpcType.QUEST)
        )
    }
}
package core.kotlinredis.service

import core.kotlinredis.entity.Npc
import core.kotlinredis.entity.NpcType
import core.kotlinredis.entity.NpcType.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@ParameterizedTest
@ValueSource(strings = ["100", "101", "102"])
annotation class ParameterizedDefaultValueTest()

@SpringBootTest
internal class NpcServiceTest @Autowired constructor(
    val npcService: NpcService
) {

    @ParameterizedDefaultValueTest
    fun `npc_추가_및_조회_기능`(id: String) {
        //given
        val entity = Npc(
            id,
            "Bob" + id,
            DEFAULT
        )

        //when
        npcService.save(entity)

        //then
        val findEntity = npcService.findNpcById(id)
        assertThat(entity.id).isEqualTo(findEntity.id)
        assertThat(entity.name).isEqualTo(findEntity.name)
        assertThat(entity.type).isEqualTo(findEntity.type)
        assertThat(findEntity).isInstanceOf(Npc::class.java)
    }

    @ParameterizedDefaultValueTest
    fun `npc_수정_기능`(id: String) {
        //given
        val entity = Npc(
            id,
            "Bob" + id,
            DEFAULT
        )
        npcService.save(entity)

        //when
        val randomType = generateRandomType()
        npcService.update(id, randomType)

        //then
        val updatedEntity = npcService.findNpcById(id)
        assertThat(entity.id).isEqualTo(updatedEntity.id)
        assertThat(entity.name).isEqualTo(updatedEntity.name)
        assertThat(updatedEntity.type).isEqualTo(randomType)
    }

    @ParameterizedDefaultValueTest
    fun `npc_삭제_기능`(id: String) {
        //given
        val entity = Npc(
            id,
            "Bob" + id,
            DEFAULT
        )
        npcService.save(entity)

        //when
        npcService.deleteById(id)

        //then
        assertThrows<RuntimeException> {
            npcService.findNpcById(id)
        }
    }

    fun generateRandomType(): NpcType {
        return when (Random().nextInt(NpcType.values().size)) {
            0 -> DEFAULT
            1 -> NORMAL
            2 -> QUEST
            else -> DEFAULT
        }
    }
}
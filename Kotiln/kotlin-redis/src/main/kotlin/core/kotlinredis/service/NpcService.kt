package core.kotlinredis.service

import core.kotlinredis.entity.Npc
import core.kotlinredis.entity.NpcType
import core.kotlinredis.repository.NpcRepository
import core.kotlinredis.repository.getByIdOrThrow
import org.springframework.stereotype.Service

@Service
class NpcService(
    val npcRepository: NpcRepository
) {

    fun findNpcById(id: String): Npc {
        return npcRepository.getByIdOrThrow(id)
    }

    fun findAllNpc(): List<Npc> {
        return npcRepository.findAll().toList()
    }

    fun save(entity: Npc): Npc {
        return npcRepository.save(entity)
    }

    fun update(id: String, type: NpcType): Npc {
        val entity = npcRepository.getByIdOrThrow(id)
        return npcRepository.save(entity.update(entity.name, type))
    }

    fun deleteById(id: String) {
        npcRepository.deleteById(id)
    }
}
package core.kotlinredis.repository

import core.kotlinredis.entity.Npc
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

fun NpcRepository.getByIdOrThrow(id: String) =
    findById(id)
        .orElseThrow {
            RuntimeException("${id}의 값을 찾을 수 없습니다")
        }

@Repository
interface NpcRepository : CrudRepository<Npc, String> {
}
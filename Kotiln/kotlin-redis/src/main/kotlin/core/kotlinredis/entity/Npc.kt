package core.kotlinredis.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

enum class NpcType(
    val description: String
) {
    DEFAULT("아직 타입이 없는 Npc"),
    NORMAL("기본 Npc"),
    QUEST("퀘스트를 주는 Npc")
}

@RedisHash("npc")
class Npc(
    @Id
    val id: String,
    var name: String,
    var type: NpcType
) {

    fun update(name: String, type: NpcType): Npc {
        this.name = name
        this.type = type
        return this
    }
}
package core.kotlinredis.controller

import core.kotlinredis.entity.Npc
import core.kotlinredis.entity.NpcType
import core.kotlinredis.service.NpcService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
@RequestMapping("/api")
class NpcController(
    val npcService: NpcService
) {
    val nameList =
        listOf("jamse", "bob", "sam", "north", "south", "east", "build", "gradle", "test", "socar", "next", "move")

    fun generateNpcType(number: Int): NpcType {
        return when (number % NpcType.values().size) {
            0 -> NpcType.DEFAULT
            1 -> NpcType.NORMAL
            2 -> NpcType.QUEST
            else -> NpcType.DEFAULT
        }
    }

    @GetMapping("/create/{id}")
    fun create(
        @PathVariable id: String
    ): ResponseEntity<Npc> {
        val index = Random.nextInt(0, nameList.size)
        val npc = Npc(id, nameList.get(index), generateNpcType(index))
        return ResponseEntity.ok(
            npcService.save(npc)
        )
    }

    @GetMapping("/read-all")
    fun readAll(): ResponseEntity<List<Npc>> {
        return ResponseEntity.ok(
            npcService.findAllNpc()
        )
    }

    @GetMapping("/read/{id}")
    fun read(
        @PathVariable id: String
    ): ResponseEntity<Npc> {
        return ResponseEntity.ok(npcService.findNpcById(id))
    }

    @GetMapping("/update/{id}/{type}")
    fun update(
        @PathVariable id: String,
        @PathVariable type: NpcType
    ): ResponseEntity<Npc> {
        return ResponseEntity.ok(
            npcService.update(id, type)
        )
    }

    @GetMapping("/delete/{id}")
    fun delete(
        @PathVariable id: String
    ): HttpStatus {
        npcService.deleteById(id)
        return HttpStatus.OK
    }
}
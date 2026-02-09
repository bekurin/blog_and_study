package core.serverdrivenui.adapter.`in`.web

import core.serverdrivenui.application.port.`in`.GetMapStatUseCase
import core.serverdrivenui.domain.model.MapStat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/maps")
class MapStatController(
    private val getMapStatUseCase: GetMapStatUseCase,
) {

    @GetMapping
    fun getMaps(@RequestParam(required = false) mode: String?): List<MapStat> =
        getMapStatUseCase.getAllMaps(mode)

    @GetMapping("/{mapId}")
    fun getMapById(@PathVariable mapId: String): ResponseEntity<MapStat> {
        val map = getMapStatUseCase.getMapById(mapId)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(map)
    }
}

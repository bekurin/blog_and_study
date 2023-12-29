package curd.redis

import org.springframework.data.geo.Distance
import org.springframework.data.geo.Point
import org.springframework.web.bind.annotation.*

@RestController
class RedisCrudController(
    private val redisCrudService: RedisCrudService
) {
    @GetMapping("/places")
    fun searchPlaces(
        @RequestParam latitude: Double,
        @RequestParam longitude: Double,
        @RequestParam radius: Double,
    ): List<Place> {
        return redisCrudService.search(Constant.PLACE, Point(latitude, longitude), Distance(radius))
    }

    @PostMapping("/places")
    fun insertPlaces(
        @RequestBody places: List<Place>
    ): Long {
        return redisCrudService.insertPlaces(Constant.PLACE, places)
    }

    @DeleteMapping("/places/{member}")
    fun deletePlace(
        @PathVariable member: String
    ): Long {
        return redisCrudService.delete(Constant.PLACE, member)
    }

    @PutMapping("/places/{member}")
    fun updatePlace(
        @PathVariable member: String,
        @RequestBody coordinate: Coordinate
    ): Long {
        return redisCrudService.update(Constant.PLACE, member, coordinate)
    }

}

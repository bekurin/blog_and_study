package core.serverdrivenui.adapter.out.persistence

import core.serverdrivenui.application.port.out.MapStatRepository
import core.serverdrivenui.domain.model.MapStat
import org.springframework.stereotype.Repository

@Repository
class MockMapStatRepository : MapStatRepository {

    private val maps = listOf(
        MapStat("map-001", "크리스탈 아케이드", "gemGrab", 125000),
        MapStat("map-002", "하드록 마인", "gemGrab", 98000),
        MapStat("map-003", "슈퍼 스타디움", "brawlBall", 156000),
        MapStat("map-004", "트리플 드리블", "brawlBall", 142000),
        MapStat("map-005", "캐널 그랜데", "bounty", 87000),
        MapStat("map-006", "레이어 케이크", "bounty", 76000),
        MapStat("map-007", "핫 포테이토", "heist", 65000),
        MapStat("map-008", "카바나 폴스", "heist", 58000),
        MapStat("map-009", "벨리볼", "knockout", 92000),
        MapStat("map-010", "골드암 걸치", "knockout", 84000),
    )

    override fun findAll(): List<MapStat> = maps

    override fun findByMode(mode: String): List<MapStat> = maps.filter { it.mode == mode }

    override fun findById(mapId: String): MapStat? = maps.find { it.mapId == mapId }
}

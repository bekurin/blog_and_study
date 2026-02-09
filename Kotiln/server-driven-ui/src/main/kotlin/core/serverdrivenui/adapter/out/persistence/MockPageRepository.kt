package core.serverdrivenui.adapter.out.persistence

import core.serverdrivenui.application.port.out.PageRepository
import core.serverdrivenui.domain.model.Component
import core.serverdrivenui.domain.model.Page
import org.springframework.stereotype.Repository

@Repository
class MockPageRepository : PageRepository {

    private val pages = mapOf(
        "dashboard" to createDashboardPage(),
        "stat-map" to createStatMapPage(),
        "stat-brawler" to createStatBrawlerPage(),
        "batch" to createBatchPage(),
    )

    override fun findByPageKey(pageKey: String): Page? = pages[pageKey]

    private fun createDashboardPage() = Page(
        pageKey = "dashboard",
        title = "대시보드",
        components = listOf(
            Component(
                type = "row",
                props = mapOf("gutter" to listOf(16, 16)),
                children = listOf(
                    createStatisticCol("총 경기 수", 452000),
                    createStatisticCol("활성 맵", 42),
                    createStatisticCol("등록 브롤러", 82),
                    createStatisticCol("마지막 배치", "10:00 AM"),
                ),
            ),
            Component(
                type = "card",
                props = mapOf("title" to "시스템 상태"),
                children = listOf(
                    Component(
                        type = "descriptions",
                        props = mapOf(
                            "bordered" to true,
                            "column" to 2,
                            "items" to listOf(
                                mapOf("key" to "status", "label" to "서버 상태", "value" to "UP"),
                                mapOf("key" to "version", "label" to "버전", "value" to "1.0.0"),
                                mapOf("key" to "lastBatch", "label" to "마지막 배치", "value" to "2025-02-09 10:00:00"),
                                mapOf("key" to "totalLogs", "label" to "총 로그 수", "value" to "452,000"),
                            ),
                        ),
                    ),
                ),
            ),
        ),
    )

    private fun createStatisticCol(title: String, value: Any) = Component(
        type = "col",
        props = mapOf("span" to 6),
        children = listOf(
            Component(
                type = "card",
                props = emptyMap(),
                children = listOf(
                    Component(
                        type = "statistic",
                        props = mapOf("title" to title, "value" to value),
                    ),
                ),
            ),
        ),
    )

    private fun createStatMapPage() = Page(
        pageKey = "stat-map",
        title = "맵별 통계",
        components = listOf(
            Component(
                type = "card",
                props = mapOf("title" to "검색 필터"),
                children = listOf(
                    Component(
                        type = "form",
                        props = mapOf("layout" to "inline"),
                        children = listOf(
                            Component(
                                type = "select",
                                props = mapOf(
                                    "name" to "mode",
                                    "label" to "게임 모드",
                                    "placeholder" to "모드 선택",
                                    "options" to listOf(
                                        mapOf("value" to "gemGrab", "label" to "젬 그랩"),
                                        mapOf("value" to "brawlBall", "label" to "브롤볼"),
                                        mapOf("value" to "bounty", "label" to "바운티"),
                                        mapOf("value" to "heist", "label" to "하이스트"),
                                        mapOf("value" to "knockout", "label" to "녹아웃"),
                                    ),
                                ),
                            ),
                            Component(
                                type = "button",
                                props = mapOf(
                                    "label" to "검색",
                                    "buttonType" to "primary",
                                    "action" to mapOf("type" to "submit"),
                                ),
                            ),
                        ),
                    ),
                ),
            ),
            Component(
                type = "card",
                props = mapOf("title" to "맵 목록"),
                children = listOf(
                    Component(
                        type = "table",
                        props = mapOf(
                            "dataSource" to "/api/v1/maps",
                            "rowKey" to "mapId",
                            "columns" to listOf(
                                mapOf("key" to "mapName", "title" to "맵 이름", "dataIndex" to "mapName"),
                                mapOf("key" to "mode", "title" to "모드", "dataIndex" to "mode"),
                                mapOf(
                                    "key" to "totalGames",
                                    "title" to "총 경기수",
                                    "dataIndex" to "totalGames",
                                    "render" to mapOf("type" to "number", "format" to "comma"),
                                ),
                                mapOf(
                                    "key" to "actions",
                                    "title" to "관리",
                                    "render" to mapOf(
                                        "type" to "button-group",
                                        "children" to listOf(
                                            mapOf(
                                                "type" to "button",
                                                "props" to mapOf(
                                                    "label" to "상세",
                                                    "buttonType" to "link",
                                                    "action" to mapOf("type" to "navigate", "path" to "/statistics/maps/\${mapId}"),
                                                ),
                                            ),
                                        ),
                                    ),
                                ),
                            ),
                            "pagination" to mapOf("pageSize" to 10),
                        ),
                    ),
                ),
            ),
        ),
    )

    private fun createStatBrawlerPage() = Page(
        pageKey = "stat-brawler",
        title = "브롤러 통계",
        components = listOf(
            Component(
                type = "card",
                props = mapOf("title" to "검색 필터"),
                children = listOf(
                    Component(
                        type = "form",
                        props = mapOf("layout" to "inline"),
                        children = listOf(
                            Component(
                                type = "input",
                                props = mapOf(
                                    "name" to "brawlerName",
                                    "label" to "브롤러 이름",
                                    "placeholder" to "브롤러 검색",
                                ),
                            ),
                            Component(
                                type = "select",
                                props = mapOf(
                                    "name" to "tier",
                                    "label" to "티어",
                                    "placeholder" to "티어 선택",
                                    "options" to listOf(
                                        mapOf("value" to "OP", "label" to "OP"),
                                        mapOf("value" to "1T", "label" to "1 Tier"),
                                        mapOf("value" to "2T", "label" to "2 Tier"),
                                        mapOf("value" to "3T", "label" to "3 Tier"),
                                        mapOf("value" to "4T", "label" to "4 Tier"),
                                    ),
                                ),
                            ),
                            Component(
                                type = "button",
                                props = mapOf(
                                    "label" to "검색",
                                    "buttonType" to "primary",
                                    "action" to mapOf("type" to "submit"),
                                ),
                            ),
                        ),
                    ),
                ),
            ),
            Component(
                type = "card",
                props = mapOf("title" to "브롤러 목록"),
                children = listOf(
                    Component(
                        type = "table",
                        props = mapOf(
                            "dataSource" to "/api/v1/brawlers",
                            "rowKey" to "brawlerId",
                            "columns" to listOf(
                                mapOf("key" to "brawlerName", "title" to "브롤러", "dataIndex" to "brawlerName"),
                                mapOf(
                                    "key" to "winRate",
                                    "title" to "승률",
                                    "dataIndex" to "winRate",
                                    "render" to mapOf("type" to "number", "format" to "percent"),
                                ),
                                mapOf(
                                    "key" to "pickRate",
                                    "title" to "픽률",
                                    "dataIndex" to "pickRate",
                                    "render" to mapOf("type" to "number", "format" to "percent"),
                                ),
                                mapOf(
                                    "key" to "tier",
                                    "title" to "티어",
                                    "dataIndex" to "tier",
                                    "render" to mapOf("type" to "tag"),
                                ),
                                mapOf(
                                    "key" to "totalPick",
                                    "title" to "총 픽",
                                    "dataIndex" to "totalPick",
                                    "render" to mapOf("type" to "number", "format" to "comma"),
                                ),
                            ),
                            "pagination" to mapOf("pageSize" to 20),
                        ),
                    ),
                ),
            ),
        ),
    )

    private fun createBatchPage() = Page(
        pageKey = "batch",
        title = "배치 관리",
        components = listOf(
            Component(
                type = "card",
                props = mapOf("title" to "배치 작업 현황"),
                children = listOf(
                    Component(
                        type = "descriptions",
                        props = mapOf(
                            "bordered" to true,
                            "column" to 2,
                            "items" to listOf(
                                mapOf("key" to "lastRun", "label" to "마지막 실행", "value" to "2025-02-09 10:00:00"),
                                mapOf("key" to "status", "label" to "상태", "value" to "SUCCESS"),
                                mapOf("key" to "duration", "label" to "소요 시간", "value" to "3분 24초"),
                                mapOf("key" to "processedRecords", "label" to "처리 건수", "value" to "5,234"),
                            ),
                        ),
                    ),
                ),
            ),
            Component(
                type = "card",
                props = mapOf("title" to "배치 작업 목록"),
                children = listOf(
                    Component(
                        type = "table",
                        props = mapOf(
                            "dataSource" to "/api/v1/batch/jobs",
                            "rowKey" to "jobId",
                            "columns" to listOf(
                                mapOf("key" to "jobName", "title" to "작업명", "dataIndex" to "jobName"),
                                mapOf(
                                    "key" to "status",
                                    "title" to "상태",
                                    "dataIndex" to "status",
                                    "render" to mapOf("type" to "tag"),
                                ),
                                mapOf("key" to "startTime", "title" to "시작 시간", "dataIndex" to "startTime"),
                                mapOf("key" to "endTime", "title" to "종료 시간", "dataIndex" to "endTime"),
                                mapOf(
                                    "key" to "actions",
                                    "title" to "관리",
                                    "render" to mapOf(
                                        "type" to "button-group",
                                        "children" to listOf(
                                            mapOf(
                                                "type" to "button",
                                                "props" to mapOf(
                                                    "label" to "재실행",
                                                    "buttonType" to "primary",
                                                    "action" to mapOf(
                                                        "type" to "api",
                                                        "method" to "POST",
                                                        "endpoint" to "/api/v1/batch/jobs/\${jobId}/restart",
                                                        "confirm" to "배치 작업을 재실행하시겠습니까?",
                                                    ),
                                                ),
                                            ),
                                        ),
                                    ),
                                ),
                            ),
                            "pagination" to mapOf("pageSize" to 10),
                        ),
                    ),
                ),
            ),
        ),
    )
}

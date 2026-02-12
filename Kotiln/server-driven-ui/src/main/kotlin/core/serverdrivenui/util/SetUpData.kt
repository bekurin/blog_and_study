package core.serverdrivenui.util

import core.serverdrivenui.adapter.out.persistence.entity.*
import core.serverdrivenui.adapter.out.persistence.repository.*
import core.serverdrivenui.domain.model.BatchStatus
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Component
class SetUpData(
    private val pageJpaRepository: PageJpaRepository,
    private val componentJpaRepository: ComponentJpaRepository,
    private val layoutJpaRepository: LayoutJpaRepository,
    private val mapStatJpaRepository: MapStatJpaRepository,
    private val brawlerJpaRepository: BrawlerJpaRepository,
    private val batchJobJpaRepository: BatchJobJpaRepository,
) {

    @PostConstruct
    @Transactional
    fun init() {
        setupLayout()
        setupPages()
        setupMapStats()
        setupBrawlers()
        setupBatchJobs()
    }

    private fun setupLayout() {
        // Header Component (자식을 먼저 생성하고 부모에 추가 - cascade로 함께 저장)
        val avatarComponent = ComponentEntity(type = "avatar").apply {
            addProp("name", "Admin")
        }

        val headerComponent = ComponentEntity(type = "header").apply {
            addProp("title", "BrawlStats Backoffice")
            addProp("logo", "/images/logo.png")
            addChild(avatarComponent)
        }
        componentJpaRepository.save(headerComponent)

        // Sider (Menu) Component
        val menuComponent = ComponentEntity(type = "menu").apply {
            addProp("defaultSelectedKey", "dashboard")
        }

        // Menu Items - 부모 메뉴 먼저 생성
        val dashboardMenu = MenuItemEntity(
            component = menuComponent,
            itemKey = "dashboard",
            label = "대시보드",
            icon = "dashboard",
            path = "/dashboard",
            sortOrder = 0,
        )

        val statisticsMenu = MenuItemEntity(
            component = menuComponent,
            itemKey = "statistics",
            label = "통계 관리",
            icon = "bar-chart",
            sortOrder = 1,
        )

        // 서브메뉴 추가
        statisticsMenu.addChild(
            MenuItemEntity(
                component = menuComponent,
                itemKey = "stat-map",
                label = "맵별 통계",
                path = "/statistics/maps",
                sortOrder = 0,
            )
        )
        statisticsMenu.addChild(
            MenuItemEntity(
                component = menuComponent,
                itemKey = "stat-brawler",
                label = "브롤러 통계",
                path = "/statistics/brawlers",
                sortOrder = 1,
            )
        )
        statisticsMenu.addChild(
            MenuItemEntity(
                component = menuComponent,
                itemKey = "stat-combination",
                label = "조합 통계",
                path = "/statistics/combinations",
                sortOrder = 2,
            )
        )

        val batchMenu = MenuItemEntity(
            component = menuComponent,
            itemKey = "batch",
            label = "배치 관리",
            icon = "schedule",
            path = "/batch",
            sortOrder = 2,
        )

        val settingsMenu = MenuItemEntity(
            component = menuComponent,
            itemKey = "settings",
            label = "설정",
            icon = "setting",
            path = "/settings",
            sortOrder = 3,
        )

        menuComponent.menuItems.addAll(listOf(dashboardMenu, statisticsMenu, batchMenu, settingsMenu))
        componentJpaRepository.save(menuComponent)

        // Layout
        val layout = LayoutEntity(
            layoutKey = "default",
            headerComponent = headerComponent,
            siderComponent = menuComponent,
        )
        layoutJpaRepository.save(layout)
    }

    private fun setupPages() {
        setupDashboardPage()
        setupStatMapPage()
        setupStatBrawlerPage()
        setupBatchPage()
    }

    private fun setupDashboardPage() {
        // Statistic Cards Row - cascade로 자식들 함께 저장
        val row = ComponentEntity(type = "row").apply {
            addChild(createStatisticCard("총 경기 수", "452000"))
            addChild(createStatisticCard("활성 맵", "42"))
            addChild(createStatisticCard("등록 브롤러", "82"))
            addChild(createStatisticCard("마지막 배치", "10:00 AM"))
        }
        componentJpaRepository.save(row)

        // System Status Card
        val systemCard = ComponentEntity(type = "card").apply {
            addProp("title", "시스템 상태")
            addChild(
                ComponentEntity(type = "descriptions").apply {
                    addProp("bordered", "true", PropValueType.BOOLEAN)
                    addProp("column", "2", PropValueType.NUMBER)
                }
            )
        }
        componentJpaRepository.save(systemCard)

        // Page
        val page = PageEntity(pageKey = "dashboard", title = "대시보드")
        page.pageComponents.add(PageComponentEntity(page = page, component = row, sortOrder = 0))
        page.pageComponents.add(PageComponentEntity(page = page, component = systemCard, sortOrder = 1))
        pageJpaRepository.save(page)
    }

    private fun createStatisticCard(title: String, value: String): ComponentEntity {
        return ComponentEntity(type = "col").apply {
            addProp("span", "6", PropValueType.NUMBER)
            addChild(
                ComponentEntity(type = "card").apply {
                    addChild(
                        ComponentEntity(type = "statistic").apply {
                            addProp("title", title)
                            addProp("value", value)
                        }
                    )
                }
            )
        }
    }

    private fun setupStatMapPage() {
        // Filter Card
        val filterCard = ComponentEntity(type = "card").apply {
            addProp("title", "검색 필터")
            addChild(
                ComponentEntity(type = "form").apply {
                    addProp("layout", "inline")
                    addChild(
                        ComponentEntity(type = "select").apply {
                            addProp("name", "mode")
                            addProp("label", "게임 모드")
                            addProp("placeholder", "모드 선택")
                        }
                    )
                    addChild(
                        ComponentEntity(type = "button").apply {
                            addProp("label", "검색")
                            addProp("buttonType", "primary")
                        }
                    )
                }
            )
        }
        componentJpaRepository.save(filterCard)

        // List Card
        val listCard = ComponentEntity(type = "card").apply {
            addProp("title", "맵 목록")
            addChild(
                ComponentEntity(type = "table").apply {
                    addProp("dataSource", "/api/v1/maps")
                    addProp("rowKey", "mapId")
                }
            )
        }
        componentJpaRepository.save(listCard)

        // Page
        val page = PageEntity(pageKey = "stat-map", title = "맵별 통계")
        page.pageComponents.add(PageComponentEntity(page = page, component = filterCard, sortOrder = 0))
        page.pageComponents.add(PageComponentEntity(page = page, component = listCard, sortOrder = 1))
        pageJpaRepository.save(page)
    }

    private fun setupStatBrawlerPage() {
        // Filter Card
        val filterCard = ComponentEntity(type = "card").apply {
            addProp("title", "검색 필터")
            addChild(
                ComponentEntity(type = "form").apply {
                    addProp("layout", "inline")
                    addChild(
                        ComponentEntity(type = "input").apply {
                            addProp("name", "brawlerName")
                            addProp("label", "브롤러 이름")
                            addProp("placeholder", "브롤러 검색")
                        }
                    )
                    addChild(
                        ComponentEntity(type = "select").apply {
                            addProp("name", "tier")
                            addProp("label", "티어")
                            addProp("placeholder", "티어 선택")
                        }
                    )
                    addChild(
                        ComponentEntity(type = "button").apply {
                            addProp("label", "검색")
                            addProp("buttonType", "primary")
                        }
                    )
                }
            )
        }
        componentJpaRepository.save(filterCard)

        // List Card
        val listCard = ComponentEntity(type = "card").apply {
            addProp("title", "브롤러 목록")
            addChild(
                ComponentEntity(type = "table").apply {
                    addProp("dataSource", "/api/v1/brawlers")
                    addProp("rowKey", "brawlerId")
                }
            )
        }
        componentJpaRepository.save(listCard)

        // Page
        val page = PageEntity(pageKey = "stat-brawler", title = "브롤러 통계")
        page.pageComponents.add(PageComponentEntity(page = page, component = filterCard, sortOrder = 0))
        page.pageComponents.add(PageComponentEntity(page = page, component = listCard, sortOrder = 1))
        pageJpaRepository.save(page)
    }

    private fun setupBatchPage() {
        // Status Card
        val statusCard = ComponentEntity(type = "card").apply {
            addProp("title", "배치 작업 현황")
            addChild(
                ComponentEntity(type = "descriptions").apply {
                    addProp("bordered", "true", PropValueType.BOOLEAN)
                    addProp("column", "2", PropValueType.NUMBER)
                }
            )
        }
        componentJpaRepository.save(statusCard)

        // List Card
        val listCard = ComponentEntity(type = "card").apply {
            addProp("title", "배치 작업 목록")
            addChild(
                ComponentEntity(type = "table").apply {
                    addProp("dataSource", "/api/v1/batch/jobs")
                    addProp("rowKey", "jobId")
                }
            )
        }
        componentJpaRepository.save(listCard)

        // Page
        val page = PageEntity(pageKey = "batch", title = "배치 관리")
        page.pageComponents.add(PageComponentEntity(page = page, component = statusCard, sortOrder = 0))
        page.pageComponents.add(PageComponentEntity(page = page, component = listCard, sortOrder = 1))
        pageJpaRepository.save(page)
    }

    private fun setupMapStats() {
        val maps = listOf(
            MapStatEntity("map-001", "크리스탈 아케이드", "gemGrab", 125000),
            MapStatEntity("map-002", "하드록 마인", "gemGrab", 98000),
            MapStatEntity("map-003", "슈퍼 스타디움", "brawlBall", 156000),
            MapStatEntity("map-004", "트리플 드리블", "brawlBall", 142000),
            MapStatEntity("map-005", "캐널 그랜데", "bounty", 87000),
            MapStatEntity("map-006", "레이어 케이크", "bounty", 76000),
            MapStatEntity("map-007", "핫 포테이토", "heist", 65000),
            MapStatEntity("map-008", "카바나 폴스", "heist", 58000),
            MapStatEntity("map-009", "벨리볼", "knockout", 92000),
            MapStatEntity("map-010", "골드암 걸치", "knockout", 84000),
        )
        mapStatJpaRepository.saveAll(maps)
    }

    private fun setupBrawlers() {
        val brawlers = listOf(
            BrawlerEntity("brawler-001", "셸리", 0.52, 0.08, "2T", 45000),
            BrawlerEntity("brawler-002", "니타", 0.54, 0.06, "2T", 38000),
            BrawlerEntity("brawler-003", "콜트", 0.48, 0.07, "3T", 42000),
            BrawlerEntity("brawler-004", "불", 0.51, 0.05, "2T", 31000),
            BrawlerEntity("brawler-005", "제시", 0.53, 0.06, "2T", 36000),
            BrawlerEntity("brawler-006", "브록", 0.49, 0.05, "3T", 29000),
            BrawlerEntity("brawler-007", "다이나마이크", 0.47, 0.04, "3T", 25000),
            BrawlerEntity("brawler-008", "보", 0.55, 0.07, "1T", 43000),
            BrawlerEntity("brawler-009", "틱", 0.46, 0.04, "4T", 22000),
            BrawlerEntity("brawler-010", "8비트", 0.50, 0.05, "2T", 30000),
            BrawlerEntity("brawler-011", "엠즈", 0.52, 0.06, "2T", 35000),
            BrawlerEntity("brawler-012", "엘 프리모", 0.53, 0.07, "2T", 40000),
            BrawlerEntity("brawler-013", "바르리", 0.48, 0.04, "3T", 24000),
            BrawlerEntity("brawler-014", "포코", 0.51, 0.05, "2T", 28000),
            BrawlerEntity("brawler-015", "로사", 0.56, 0.08, "1T", 48000),
            BrawlerEntity("brawler-016", "리코", 0.49, 0.05, "3T", 27000),
            BrawlerEntity("brawler-017", "데릴", 0.54, 0.06, "1T", 37000),
            BrawlerEntity("brawler-018", "페니", 0.50, 0.05, "2T", 29000),
            BrawlerEntity("brawler-019", "칼", 0.47, 0.04, "4T", 21000),
            BrawlerEntity("brawler-020", "재키", 0.52, 0.06, "2T", 34000),
        )
        brawlerJpaRepository.saveAll(brawlers)
    }

    private fun setupBatchJobs() {
        val jobs = listOf(
            BatchJobEntity(
                jobId = "job-001",
                jobName = "맵 통계 수집",
                status = BatchStatus.SUCCESS,
                startTime = LocalDateTime.of(2025, 2, 9, 10, 0, 0),
                endTime = LocalDateTime.of(2025, 2, 9, 10, 3, 24),
            ),
            BatchJobEntity(
                jobId = "job-002",
                jobName = "브롤러 승률 계산",
                status = BatchStatus.SUCCESS,
                startTime = LocalDateTime.of(2025, 2, 9, 10, 5, 0),
                endTime = LocalDateTime.of(2025, 2, 9, 10, 8, 15),
            ),
            BatchJobEntity(
                jobId = "job-003",
                jobName = "조합 분석",
                status = BatchStatus.FAILED,
                startTime = LocalDateTime.of(2025, 2, 9, 10, 10, 0),
                endTime = LocalDateTime.of(2025, 2, 9, 10, 12, 30),
            ),
            BatchJobEntity(
                jobId = "job-004",
                jobName = "데이터 정리",
                status = BatchStatus.PENDING,
                startTime = null,
                endTime = null,
            ),
        )
        batchJobJpaRepository.saveAll(jobs)
    }
}

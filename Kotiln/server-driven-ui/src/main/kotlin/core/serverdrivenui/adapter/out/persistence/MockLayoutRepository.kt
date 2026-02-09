package core.serverdrivenui.adapter.out.persistence

import core.serverdrivenui.application.port.out.LayoutRepository
import core.serverdrivenui.domain.model.Component
import core.serverdrivenui.domain.model.Layout
import core.serverdrivenui.domain.model.MenuItem
import org.springframework.stereotype.Repository

@Repository
class MockLayoutRepository : LayoutRepository {

    override fun findLayout(): Layout = Layout(
        header = Component(
            type = "header",
            props = mapOf(
                "title" to "BrawlStats Backoffice",
                "logo" to "/images/logo.png",
            ),
            children = listOf(
                Component(
                    type = "avatar",
                    props = mapOf(
                        "name" to "Admin",
                        "dropdown" to listOf(
                            mapOf("key" to "profile", "label" to "프로필", "icon" to "user"),
                            mapOf("key" to "logout", "label" to "로그아웃", "icon" to "logout"),
                        ),
                    ),
                ),
            ),
        ),
        sider = Component(
            type = "menu",
            props = mapOf(
                "defaultSelectedKey" to "dashboard",
                "defaultOpenKeys" to listOf("statistics"),
            ),
            items = listOf(
                MenuItem(
                    key = "dashboard",
                    label = "대시보드",
                    icon = "dashboard",
                    path = "/dashboard",
                ),
                MenuItem(
                    key = "statistics",
                    label = "통계 관리",
                    icon = "bar-chart",
                    children = listOf(
                        MenuItem(key = "stat-map", label = "맵별 통계", path = "/statistics/maps"),
                        MenuItem(key = "stat-brawler", label = "브롤러 통계", path = "/statistics/brawlers"),
                        MenuItem(key = "stat-combination", label = "조합 통계", path = "/statistics/combinations"),
                    ),
                ),
                MenuItem(
                    key = "batch",
                    label = "배치 관리",
                    icon = "schedule",
                    path = "/batch",
                ),
                MenuItem(
                    key = "settings",
                    label = "설정",
                    icon = "setting",
                    path = "/settings",
                ),
            ),
        ),
    )
}

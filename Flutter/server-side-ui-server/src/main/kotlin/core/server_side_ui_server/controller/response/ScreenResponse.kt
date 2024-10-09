package core.server_side_ui_server.controller.response

data class ScreenResponse(
    val header: Component?,
    val body: Component?,
    val footer: Component?,
    val metadata: Metadata
)

data class Metadata(
    val username: String,
)

data class AppBarComponent(
    override val type: Type,
    val title: String,
) : Component

data class ContainerComponent(
    override val type: Type,
    val padding: String,
    val color: Color,
    val children: List<Component>
) : Component

data class GridComponent(
    override val type: Type,
    val padding: Int,
    val crossAxisSpacing: Int,
    val mainAxisSpacing: Int,
    val crossAxisCount: Int,
    val children: List<Component>,
) : Component

data class Color(
    val value: String,
    val alpha: String
)

data class TextButtonComponent(
    override val type: Type,
    val text: String,
    val route: String,
) : Component

data class TextFieldComponent(
    override val type: Type,
    val labelText: String,
    val enabled: Boolean,
) : Component

interface Component {
    val type: Type
}

enum class Type(
    description: String,
) {
    APP_BAR("앱 상단 바"),
    COLUMN("컬럼"),
    CONTAINER("컨테이너"),
    GRID_VIEW("그리드 뷰"),
    TEXT_BUTTON("버튼"),
    TEXT_FILED("조회용 필드"),
}

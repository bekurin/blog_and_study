package core.serverdrivenui.domain.model

data class Page(
    val pageKey: String,
    val title: String,
    val components: List<Component>,
)

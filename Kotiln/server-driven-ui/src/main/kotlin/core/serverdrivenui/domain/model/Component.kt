package core.serverdrivenui.domain.model

data class Component(
    val type: String,
    val props: Map<String, Any?> = emptyMap(),
    val children: List<Component> = emptyList(),
    val items: List<MenuItem>? = null,
)

data class MenuItem(
    val key: String,
    val label: String,
    val icon: String? = null,
    val path: String? = null,
    val children: List<MenuItem>? = null,
)

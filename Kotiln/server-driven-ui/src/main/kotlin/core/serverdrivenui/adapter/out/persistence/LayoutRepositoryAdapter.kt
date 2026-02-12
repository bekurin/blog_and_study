package core.serverdrivenui.adapter.out.persistence

import core.serverdrivenui.adapter.out.persistence.entity.ComponentEntity
import core.serverdrivenui.adapter.out.persistence.entity.MenuItemEntity
import core.serverdrivenui.adapter.out.persistence.repository.LayoutJpaRepository
import core.serverdrivenui.application.port.out.LayoutRepository
import core.serverdrivenui.domain.model.Component
import core.serverdrivenui.domain.model.Layout
import core.serverdrivenui.domain.model.MenuItem
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository

@Primary
@Repository
class LayoutRepositoryAdapter(
    private val layoutJpaRepository: LayoutJpaRepository,
) : LayoutRepository {

    override fun findLayout(): Layout {
        val entity = layoutJpaRepository.findByLayoutKey("default")
            ?: throw IllegalStateException("Default layout not found")

        return Layout(
            header = entity.headerComponent?.let { toComponent(it) }
                ?: throw IllegalStateException("Header component not found"),
            sider = entity.siderComponent?.let { toComponentWithMenuItems(it) }
                ?: throw IllegalStateException("Sider component not found"),
        )
    }

    private fun toComponent(entity: ComponentEntity): Component {
        return Component(
            type = entity.type,
            props = entity.props.associate { prop ->
                prop.propKey to prop.toTypedValue()
            },
            children = entity.children.map { toComponent(it) },
            items = null,
        )
    }

    private fun toComponentWithMenuItems(entity: ComponentEntity): Component {
        val rootMenuItems = entity.menuItems.filter { it.parent == null }
        return Component(
            type = entity.type,
            props = entity.props.associate { prop ->
                prop.propKey to prop.toTypedValue()
            },
            children = entity.children.map { toComponent(it) },
            items = rootMenuItems.map { toMenuItem(it) },
        )
    }

    private fun toMenuItem(entity: MenuItemEntity): MenuItem {
        return MenuItem(
            key = entity.itemKey,
            label = entity.label,
            icon = entity.icon,
            path = entity.path,
            children = entity.children.takeIf { it.isNotEmpty() }?.map { toMenuItem(it) },
        )
    }
}

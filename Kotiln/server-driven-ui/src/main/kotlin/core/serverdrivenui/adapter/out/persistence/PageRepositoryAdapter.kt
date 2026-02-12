package core.serverdrivenui.adapter.out.persistence

import core.serverdrivenui.adapter.out.persistence.entity.ComponentEntity
import core.serverdrivenui.adapter.out.persistence.entity.PropValueType
import core.serverdrivenui.adapter.out.persistence.repository.PageJpaRepository
import core.serverdrivenui.application.port.out.PageRepository
import core.serverdrivenui.domain.model.Component
import core.serverdrivenui.domain.model.Page
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository

@Primary
@Repository
class PageRepositoryAdapter(
    private val pageJpaRepository: PageJpaRepository,
) : PageRepository {

    override fun findByPageKey(pageKey: String): Page? {
        return pageJpaRepository.findByPageKey(pageKey)?.let { entity ->
            Page(
                pageKey = entity.pageKey,
                title = entity.title,
                components = entity.pageComponents.map { pc ->
                    toComponent(pc.component)
                },
            )
        }
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
}

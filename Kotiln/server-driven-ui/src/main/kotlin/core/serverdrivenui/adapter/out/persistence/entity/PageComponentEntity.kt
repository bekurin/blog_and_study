package core.serverdrivenui.adapter.out.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "page_component")
class PageComponentEntity(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id", nullable = false)
    val page: PageEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_id", nullable = false)
    val component: ComponentEntity,

    @Column(name = "sort_order", nullable = false)
    val sortOrder: Int = 0,
) : BaseEntity()

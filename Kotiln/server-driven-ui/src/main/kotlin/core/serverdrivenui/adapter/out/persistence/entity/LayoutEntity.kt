package core.serverdrivenui.adapter.out.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "layout")
class LayoutEntity(
    @Column(name = "layout_key", unique = true, nullable = false, length = 100)
    val layoutKey: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "header_component_id")
    val headerComponent: ComponentEntity?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sider_component_id")
    val siderComponent: ComponentEntity?,
) : BaseEntity()

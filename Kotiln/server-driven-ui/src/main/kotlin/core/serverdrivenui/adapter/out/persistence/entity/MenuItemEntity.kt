package core.serverdrivenui.adapter.out.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "menu_item")
class MenuItemEntity(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_id", nullable = false)
    val component: ComponentEntity,

    @Column(name = "item_key", nullable = false, length = 100)
    val itemKey: String,

    @Column(nullable = false)
    val label: String,

    @Column(length = 100)
    val icon: String? = null,

    @Column(length = 255)
    val path: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent: MenuItemEntity? = null,

    @Column(name = "sort_order", nullable = false)
    val sortOrder: Int = 0,

    @OneToMany(mappedBy = "parent", cascade = [CascadeType.ALL], orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    val children: MutableList<MenuItemEntity> = mutableListOf(),
) : BaseEntity() {
    fun addChild(child: MenuItemEntity) {
        children.add(child)
        child.parent = this
    }
}

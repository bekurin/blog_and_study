package core.serverdrivenui.adapter.out.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "component")
class ComponentEntity(
    @Column(nullable = false, length = 100)
    val type: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent: ComponentEntity? = null,

    @Column(name = "sort_order", nullable = false)
    val sortOrder: Int = 0,

    @OneToMany(mappedBy = "parent", cascade = [CascadeType.ALL], orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    val children: MutableList<ComponentEntity> = mutableListOf(),

    @OneToMany(mappedBy = "component", cascade = [CascadeType.ALL], orphanRemoval = true)
    val props: MutableList<ComponentPropEntity> = mutableListOf(),

    @OneToMany(mappedBy = "component", cascade = [CascadeType.ALL], orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    val menuItems: MutableList<MenuItemEntity> = mutableListOf(),
) : BaseEntity() {
    fun addChild(child: ComponentEntity) {
        children.add(child)
        child.parent = this
    }

    fun addProp(key: String, value: String?, valueType: PropValueType = PropValueType.STRING) {
        props.add(
            ComponentPropEntity(
                component = this,
                propKey = key,
                propValue = value,
                valueType = valueType,
            )
        )
    }
}

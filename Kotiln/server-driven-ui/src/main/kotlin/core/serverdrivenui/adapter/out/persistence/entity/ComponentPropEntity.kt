package core.serverdrivenui.adapter.out.persistence.entity

import jakarta.persistence.*

@Entity
@Table(
    name = "component_prop",
    uniqueConstraints = [UniqueConstraint(columnNames = ["component_id", "prop_key"])]
)
class ComponentPropEntity(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_id", nullable = false)
    val component: ComponentEntity,

    @Column(name = "prop_key", nullable = false, length = 100)
    val propKey: String,

    @Column(name = "prop_value", length = 1000)
    val propValue: String?,

    @Enumerated(EnumType.STRING)
    @Column(name = "value_type", nullable = false, length = 20)
    val valueType: PropValueType = PropValueType.STRING,
) : BaseEntity() {
    fun toTypedValue(): Any? {
        val value = propValue ?: return null
        return when (valueType) {
            PropValueType.STRING -> value
            PropValueType.NUMBER -> value.toDoubleOrNull() ?: value.toLongOrNull()
            PropValueType.BOOLEAN -> value.toBoolean()
        }
    }
}

enum class PropValueType {
    STRING,
    NUMBER,
    BOOLEAN,
}

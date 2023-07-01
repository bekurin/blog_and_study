package com.jpa.kotlinjpa.entity

import jakarta.persistence.*
import org.hibernate.proxy.HibernateProxy
import org.springframework.data.domain.Persistable
import java.util.*

@MappedSuperclass
class PrimaryKeyEntity : Persistable<Long>, TimestampEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    protected val id: Long = 0L

    @Transient
    private var _isNew = true

    override fun getId(): Long {
        return id
    }

    override fun isNew(): Boolean {
        return _isNew
    }

    override fun equals(other: Any?): Boolean {
        other ?: return false

        if (other !is HibernateProxy && this::class != other::class) {
            return false
        }

        return id == getIdentifier(other)
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

    private fun getIdentifier(obj: Any): Any {
        return if (obj is HibernateProxy) {
            obj.hibernateLazyInitializer.identifier
        } else {
            (obj as PrimaryKeyEntity).id
        }
    }

    @PostPersist
    @PostLoad
    protected fun load() {
        _isNew = false
    }
}

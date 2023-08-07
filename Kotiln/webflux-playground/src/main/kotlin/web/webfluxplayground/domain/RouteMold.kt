package web.webfluxplayground.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.time.LocalDateTime
import java.util.*

@Entity
class RouteMold(
    availableStartAt: LocalDateTime,
    unavailableStartAt: LocalDateTime,
    unavailableEndAt: LocalDateTime,
    routeId: String = UUID.randomUUID().toString(),
    name: String
) : BaseEntity() {
    @Column(nullable = false)
    var availableStartAt: LocalDateTime = availableStartAt
        protected set

    @Column(nullable = false)
    var unavailableStartAt: LocalDateTime = unavailableStartAt
        protected set

    @Column(nullable = false)
    var unavailableEndAt: LocalDateTime = unavailableEndAt
        protected set

    @Column(nullable = false)
    var routeId: String = routeId
        protected set

    @Column(nullable = false)
    var name: String = name
        protected set
}

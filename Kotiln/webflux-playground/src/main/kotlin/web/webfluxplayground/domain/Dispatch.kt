package web.webfluxplayground.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class Dispatch(
    vehicleId: Int,
    routeId: String
) : BaseEntity() {
    @Column(nullable = false)
    var vehicleId: Int = vehicleId
        protected set

    @Column(nullable = false)
    var routeId: String = routeId
        protected set
}

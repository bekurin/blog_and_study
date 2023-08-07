package web.webfluxplayground.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class Station(
    sortNo: Int,
    placeId: Int,
    routeId: String
) : BaseEntity() {
    @Column(nullable = false)
    var sortNo: Int = sortNo
        protected set

    @Column(nullable = false)
    var placeId: Int = placeId
        protected set

    @Column(nullable = false)
    var routeId: String = routeId
        protected set
}

package web.webfluxplayground.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class Place(
    name: String,
    latitude: Double,
    longitude: Double
) : BaseEntity() {
    @Column(nullable = false)
    var name: String = name
        protected set

    @Column(nullable = false)
    var latitude: Double = latitude
        protected set

    @Column(nullable = false)
    var longitude: Double = longitude
        protected set
}

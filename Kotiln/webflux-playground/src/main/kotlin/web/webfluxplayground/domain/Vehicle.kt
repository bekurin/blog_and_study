package web.webfluxplayground.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class Vehicle(
    number: String,
    capacity: Int
) : BaseEntity() {
    @Column(nullable = false)
    var number: String = number
        protected set

    @Column(nullable = false)
    var capacity: Int = capacity
        protected set
}

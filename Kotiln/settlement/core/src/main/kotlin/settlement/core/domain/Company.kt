package settlement.core.domain

import jakarta.persistence.Entity

@Entity
class Company(
    name: String,
    code: String,
): BaseEntity() {
    var name: String = name
        protected set

    var code: String = code
        protected set
}

package settlement.core.domain.company

import jakarta.persistence.Entity
import settlement.core.domain.BaseEntity

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

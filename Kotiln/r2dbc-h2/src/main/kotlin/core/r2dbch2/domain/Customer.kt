package core.r2dbch2.domain

import org.springframework.data.annotation.Id


class Customer(
    @Id
    val id: Long,
    val firstName: String,
    val lastName: String
)
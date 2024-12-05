package core.balance.repository

import core.balance.database.InMemoryDatabase
import core.balance.domain.Account
import core.balance.exception.ResourceNotFoundException
import org.springframework.stereotype.Repository

@Repository
class AccountRepository(
    private val database: InMemoryDatabase
) {
    fun findById(id: Long): Account? {
        return database.findById(id)
    }
}

package core.balance.database

import core.balance.domain.Account
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

@Component
class InMemoryDatabase {
    private val database: ConcurrentMap<Long, Account> = ConcurrentHashMap()

    fun save(account: Account): Account {
        val id = database.keys.size + 1L
        database[id] = account
        return account
    }

    fun findById(id: Long): Account? {
        return database[id]
    }

    fun delete(id: Long) {
        database.remove(id)
    }
}

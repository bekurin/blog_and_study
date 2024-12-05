package core.balance.service

import core.balance.domain.Account
import core.balance.exception.ResourceNotFoundException
import core.balance.repository.AccountRepository
import core.balance.repository.LockRepository
import org.springframework.stereotype.Service

@Service
class BalanceService(
    private val accountRepository: AccountRepository
) : LockRepository() {
    fun withdraw(id: Long, amount: Long): Account {
        val account = findByIdOrThrow(id)
        return account.withdraw(amount)
    }

    fun balance(id: Long, amount: Long): Account {
        val account = findByIdOrThrow(id)
        return account.deposit(amount)
    }

    fun transfer(fromId: Long, toId: Long, amount: Long): Any? {
        val fromAccount = findByIdOrThrow(fromId)
        val toAccount = findByIdOrThrow(toId)

        val originBalance = fromAccount.balance

        return withLock(
            action = {
                fromAccount.withdraw(amount)
                toAccount.deposit(amount)
            },
            rollback = {
                fromAccount.updateBalance(originBalance)
            }
        )
    }

    private fun findByIdOrThrow(id: Long): Account {
        val account = accountRepository.findById(id)
            ?: throw ResourceNotFoundException("Account with id $id not found")
        return account
    }
}

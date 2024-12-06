package core.balance.service

import core.balance.domain.Account
import core.balance.exception.BadRequestException
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

        validateTransfer(fromAccount, toAccount, amount)
        val originFromBalance = fromAccount.balance

        return withLock(
            action = {
                fromAccount.withdraw(amount)
                toAccount.deposit(amount)
            },
            rollback = {
                fromAccount.updateBalance(originFromBalance)
            }
        )
    }

    private fun validateTransfer(fromAccount: Account, toAccount: Account, amount: Long) {
        if (amount <= 0) throw BadRequestException("Amount must be greater than 0")
        if (fromAccount.balance < amount) throw BadRequestException("Insufficient balance")
        if (fromAccount.id == toAccount.id) throw BadRequestException("Cannot transfer to the same account")
    }

    private fun findByIdOrThrow(id: Long): Account {
        val account = accountRepository.findById(id)
            ?: throw ResourceNotFoundException("Account with id $id not found")
        return account
    }
}

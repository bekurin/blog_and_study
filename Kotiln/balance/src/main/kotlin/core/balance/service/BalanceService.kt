package core.balance.service

import core.balance.domain.Account
import core.balance.repository.AccountRepository
import core.balance.repository.LockRepository
import core.balance.util.exception.BadRequestException
import core.balance.util.exception.ResourceNotFoundException
import core.balance.util.message.MessageSourceCode.ACCOUNT_NOT_FOUND
import core.balance.util.message.MessageSourceCode.INSUFFICIENT_BALANCE
import core.balance.util.message.MessageSourceCode.NEGATIVE_AMOUNT
import core.balance.util.message.MessageSourceCode.NOT_SAME_ACCOUNT
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
        if (amount <= 0) throw BadRequestException(NEGATIVE_AMOUNT)
        if (fromAccount.balance < amount) throw BadRequestException(INSUFFICIENT_BALANCE)
        if (fromAccount.id == toAccount.id) throw BadRequestException(NOT_SAME_ACCOUNT)
    }

    private fun findByIdOrThrow(id: Long): Account {
        val account = accountRepository.findById(id)
            ?: throw ResourceNotFoundException(ACCOUNT_NOT_FOUND)
        return account
    }
}

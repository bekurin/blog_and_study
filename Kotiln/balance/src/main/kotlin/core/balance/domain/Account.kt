package core.balance.domain

import core.balance.util.exception.BadRequestException
import core.balance.util.message.MessageSourceCode.INSUFFICIENT_BALANCE
import core.balance.util.message.MessageSourceCode.NEGATIVE_AMOUNT
import core.balance.util.message.MessageSourceCode.NEGATIVE_BALANCE
import java.time.LocalDateTime

open class Account(
    val id: Long,
    balance: Long,
) {
    init {
        validateBalanceIsPositive()
    }

    var balance: Long = balance
        protected set

    var createdAt: LocalDateTime = LocalDateTime.now()
        private set

    var updatedAt: LocalDateTime = LocalDateTime.now()
        private set

    fun deposit(amount: Long): Account {
        this.balance += amount
        return this
    }

    fun withdraw(amount: Long): Account {
        val remainBalance = balance - amount
        if (remainBalance < 0) {
            throw BadRequestException(INSUFFICIENT_BALANCE)
        }
        if (amount < 0) {
            throw BadRequestException(NEGATIVE_AMOUNT)
        }
        this.balance -= amount
        return this
    }

    fun updateBalance(balance: Long): Account {
        validateBalanceIsPositive()
        this.balance = balance
        return this
    }


    private fun validateBalanceIsPositive() {
        if (balance < 0) {
            throw BadRequestException(NEGATIVE_BALANCE)
        }
    }
}

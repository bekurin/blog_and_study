package core.balance.domain

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
            throw IllegalStateException("잔액이 부족합니다.")
        }
        this.balance -= amount
        return this
    }

    fun updateBalance(balance: Long): Account {
        validateBalanceIsPositive()
        this.balance = balance
        return this
    }



    private fun validateIsSameAccount(account: Account) {
        if (this.id != account.id) {
            throw IllegalStateException("같은 계좌만 수정할 수 있습니다")
        }
    }

    private fun validateBalanceIsPositive() {
        if (balance < 0) {
            throw IllegalArgumentException("올바르지 않은 잔액입니다.")
        }
    }
}

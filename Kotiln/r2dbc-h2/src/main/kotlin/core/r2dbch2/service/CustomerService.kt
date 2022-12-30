package core.r2dbch2.service

import core.r2dbch2.sender.CustomerSender
import org.springframework.stereotype.Service

@Service
class CustomerService(
    val customerSender: CustomerSender,
) {
    fun getBalanceOrThrow(customerId: String): Double {
        return customerSender.getBalanceBy(customerId)
            .block() ?: throw RuntimeException("잔액 조회를 실패하였습니다")
    }
}
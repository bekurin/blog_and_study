package com.example.stock.facade

import com.example.stock.repository.MysqlLockRepository
import com.example.stock.service.StockService
import com.example.stock.support.Constant
import com.example.stock.support.Facade
import org.springframework.transaction.annotation.Transactional

@Facade
class NamedLockStockFacade(
    private val lockRepository: MysqlLockRepository,
    private val stockService: StockService
) {
    @Transactional
    fun decrease(id: Long, quantity: Long) {
        val key = "${Constant.STOCK_NAMED_LOCK}:$id"
        try {
            lockRepository.getLock(key)
            stockService.decrease(id, quantity)
        } finally {
            lockRepository.releaseLock(key)
        }
    }
}

package com.example.stock.facade

import com.example.stock.repository.RedisLockRepository
import com.example.stock.service.StockService
import com.example.stock.support.Facade

@Facade
class LettuceLockStockFacade(
    private val redisLockRepository: RedisLockRepository,
    private val stockService: StockService
) {

    fun decrease(id: Long, quantity: Long) {
        while (redisLockRepository.acquireLock(id).not()) {
            Thread.sleep(100)
        }
        try {
            stockService.decrease(id, quantity)
        } finally {
            redisLockRepository.releaseLock(id)
        }
    }
}

package com.example.stock.facade

import com.example.stock.service.OptimisticLockStockService
import com.example.stock.support.Facade
import jakarta.persistence.OptimisticLockException
import org.slf4j.LoggerFactory

@Facade
class OptimisticLockStockFacade(
    private val optimisticLockStockService: OptimisticLockStockService
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    fun decrease(id: Long, quantity: Long) {
        var attempt = 0L
        while (true) {
            try {
                attempt++
                optimisticLockStockService.decrease(id, quantity)
                break
            } catch (e: OptimisticLockException) {
                log.error("Optimistic lock failure at attempt {}", attempt);
                Thread.sleep((0..5000).random().toLong())
            }
        }
    }
}

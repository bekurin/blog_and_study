package com.example.stock.facade

import com.example.stock.service.StockService
import com.example.stock.support.Facade
import org.redisson.api.RedissonClient
import java.util.concurrent.TimeUnit.SECONDS

@Facade
class RedissonLockStockFacade(
    private val redissonClient: RedissonClient,
    private val stockService: StockService
) {

    fun decrease(id: Long, quantity: Long) {
        val lock = redissonClient.getLock(id.toString())

        try {
            val available = lock.tryLock(10, 1, SECONDS)
            if (available.not()) {
                return
            }
            stockService.decrease(id, quantity)
        } catch (e: InterruptedException) {
            throw RuntimeException()
        } finally {
            lock.unlock()
        }
    }
}

package com.example.distributedlock

import com.example.distributedlock.stock.StockRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StockServiceV1(
    private val distributedLockService: DistributedLockService,
    private val stockRepository: StockRepository,
) {
    private val log = LoggerFactory.getLogger(StockServiceV1::class.java)

    companion object {
        private const val STOCK_KEY = "stock_key"
        private const val STOCK_KEY_EXPIRE_SECONDS = 60L
    }

    @Transactional
    fun decreaseStock(stockId: Int, quantity: Int) {
        val isAcquireLock = distributedLockService.acquireLock(STOCK_KEY, STOCK_KEY_EXPIRE_SECONDS)
        if (isAcquireLock.not()) {
            log.info("Failed to get lock. decrease stock is skipped.")
            return
        }
        val stock = stockRepository.findById(stockId)
            .orElseThrow { throw IllegalStateException("재고 정보가 없습니다.") }
        stock.decreaseQuantity(quantity)
    }

    @Transactional
    fun increaseStock(stockId: Int, quantity: Int) {
        val isAcquireLock = distributedLockService.acquireLock(STOCK_KEY, STOCK_KEY_EXPIRE_SECONDS)
        if (isAcquireLock.not()) {
            log.info("Failed to get lock. increase stock is skipped.")
            return
        }
        val stock = stockRepository.findById(stockId)
            .orElseThrow { throw IllegalStateException("재고 정보가 없습니다.") }
        stock.increaseQuantity(quantity)
    }
}

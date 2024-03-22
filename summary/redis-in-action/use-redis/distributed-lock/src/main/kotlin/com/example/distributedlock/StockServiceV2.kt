package com.example.distributedlock

import com.example.distributedlock.aop.DistributedLock
import com.example.distributedlock.stock.Stock
import com.example.distributedlock.stock.StockRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StockServiceV2(
    private val distributedLockService: DistributedLockService,
    private val stockRepository: StockRepository,
) {
    private val log = LoggerFactory.getLogger(StockServiceV2::class.java)

    companion object {
        private const val STOCK_KEY = "stock_key"
        private const val STOCK_KEY_EXPIRE_SECONDS = 60L
    }

    @Transactional
    @DistributedLock(STOCK_KEY, STOCK_KEY_EXPIRE_SECONDS)
    fun decreaseStock(stockId: Int, quantity: Int): Stock {
        val stock = stockRepository.findById(stockId)
            .orElseThrow { throw IllegalStateException("재고 정보가 없습니다.") }
        return stock.decreaseQuantity(quantity)
    }

    @Transactional
    @DistributedLock(STOCK_KEY, STOCK_KEY_EXPIRE_SECONDS)
    fun increaseStock(stockId: Int, quantity: Int): Stock {
        val stock = stockRepository.findById(stockId)
            .orElseThrow { throw IllegalStateException("재고 정보가 없습니다.") }
        return stock.increaseQuantity(quantity)
    }
}

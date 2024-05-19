package com.example.stock.service

import com.example.stock.repository.StockRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OptimisticLockStockService(
    private val stockRepository: StockRepository
) {
    val log = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun decrease(id: Long, quantity: Long) {
        val stock = stockRepository.findOptimisticLockById(id).orElseThrow()
        val updatedStock = stock.decrease(quantity)
        log.info("stock = {}", updatedStock.quantity)
        stockRepository.saveAndFlush(updatedStock)
    }
}

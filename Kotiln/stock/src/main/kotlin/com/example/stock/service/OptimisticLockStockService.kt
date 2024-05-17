package com.example.stock.service

import com.example.stock.repository.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OptimisticLockStockService(
    private val stockRepository: StockRepository
) {
    @Transactional
    fun decrease(id: Long, quantity: Long) {
        val stock = stockRepository.findByIdWithOptimisticLock(id).orElseThrow()
        val updatedStock = stock.decrease(quantity)
        stockRepository.saveAndFlush(updatedStock)
    }
}

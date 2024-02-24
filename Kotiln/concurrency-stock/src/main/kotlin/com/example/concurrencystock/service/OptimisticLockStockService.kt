package com.example.concurrencystock.service

import com.example.concurrencystock.repository.StockRepository
import domain.Stock
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OptimisticLockStockService(
    private val stockRepository: StockRepository
) {
    @Transactional
    fun decrease(id: Long, quantity: Long): Stock {
        val stock = stockRepository.findByIdWIthOptimisticLock(id)
            .orElseThrow()
        return stock.decrease(quantity)
    }
}

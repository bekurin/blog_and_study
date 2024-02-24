package com.example.concurrencystock.service

import com.example.concurrencystock.repository.StockRepository
import domain.Stock
import org.springframework.stereotype.Service

@Service
class PessimisticLockStockService(
    private val stockRepository: StockRepository
) {

    fun decrease(id: Long, quantity: Long): Stock {
        val stock = stockRepository.findByIdWithPessimisticLock(id)
            .orElseThrow()
        return stock.decrease(quantity)
    }
}

package com.example.concurrencystock.service

import com.example.concurrencystock.repository.StockRepository
import domain.Stock
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation.REQUIRES_NEW
import org.springframework.transaction.annotation.Transactional

@Service
class StockService(
    private val stockRepository: StockRepository
) {
    @Transactional(propagation = REQUIRES_NEW)
    fun decrease(id: Long, quantity: Long): Stock {
        val stock = stockRepository.findById(id).orElseThrow()
        return stock.decrease(quantity)
    }
}

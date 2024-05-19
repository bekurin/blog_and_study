package com.example.stock.service

import com.example.stock.repository.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StockService(
    private val stockRepository: StockRepository
) {

    @Transactional
    fun decrease(id: Long, quantity: Long) {
        val stock = stockRepository.findById(id).orElseThrow()
        val updatedStock = stock.decrease(quantity)
        println("stock = ${updatedStock.quantity}")
        stockRepository.saveAndFlush(updatedStock)
    }
}

package com.example.stock.facade

import com.example.stock.service.OptimisticLockStockService
import com.example.stock.support.Facade

@Facade
class OptimisticLockStockFacade(
    private val optimisticLockStockService: OptimisticLockStockService
) {

    fun decrease(id: Long, quantity: Long) {
        while (true) {
            try {
                optimisticLockStockService.decrease(id, quantity)
                return
            } catch (exception: Exception) {
                Thread.sleep(50)
            }
        }
    }
}

package com.example.stock.repository

import com.example.stock.domain.Stock
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import java.util.*

interface StockRepository : JpaRepository<Stock, Long> {
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    fun findPessimisticWriteLockById(id: Long): Optional<Stock>

    @Lock(value = LockModeType.OPTIMISTIC)
    fun findOptimisticLockById(id: Long): Optional<Stock>
}

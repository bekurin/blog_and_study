package com.example.stock.repository

import com.example.stock.domain.Stock
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import java.util.*

interface OptimisticLockStockRepository : JpaRepository<Stock, Long> {
    @Override
    @Lock(value = LockModeType.OPTIMISTIC)
    override fun findById(id: Long): Optional<Stock>
}

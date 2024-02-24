package com.example.concurrencystock.repository

import domain.Stock
import jakarta.persistence.LockModeType.OPTIMISTIC
import jakarta.persistence.LockModeType.PESSIMISTIC_WRITE
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import java.util.*

interface StockRepository : JpaRepository<Stock, Long> {
    @Lock(value = PESSIMISTIC_WRITE)
    @Query("select s from Stock s where s.id = :id")
    fun findByIdWithPessimisticLock(id: Long): Optional<Stock>

    @Lock(value = OPTIMISTIC)
    @Query("select s from Stock s where s.id = :id")
    fun findByIdWIthOptimisticLock(id: Long): Optional<Stock>

}

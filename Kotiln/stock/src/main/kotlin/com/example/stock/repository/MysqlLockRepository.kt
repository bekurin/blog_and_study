package com.example.stock.repository

import com.example.stock.domain.Stock
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MysqlLockRepository : JpaRepository<Stock, Long> {
    @Query(value = "select get_lock(:key, 3000)", nativeQuery = true)
    fun getLock(key: String)

    @Query(value = "select release_lock(:key)", nativeQuery = true)
    fun releaseLock(key: String)
}

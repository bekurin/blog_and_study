package com.example.distributedlock.stock

import org.springframework.data.jpa.repository.JpaRepository

interface StockRepository : JpaRepository<Stock, Int>

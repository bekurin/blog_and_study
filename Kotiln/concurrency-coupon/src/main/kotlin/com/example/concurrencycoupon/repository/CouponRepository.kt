package com.example.concurrencycoupon.repository

import com.example.concurrencycoupon.domain.Coupon
import org.springframework.data.jpa.repository.JpaRepository

interface CouponRepository : JpaRepository<Coupon, Long>

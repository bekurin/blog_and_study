package com.example.api.service

import com.example.api.domain.Coupon
import com.example.api.repository.CouponRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ApplyServiceV1(
    private val couponRepository: CouponRepository
) {
    @Transactional
    fun apply(userId: Long) {
        val count = couponRepository.count()
        if (count >= 100) {
            return
        }
        couponRepository.save(Coupon(userId))
    }
}

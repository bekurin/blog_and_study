package com.example.concurrencycoupon.service

import com.example.concurrencycoupon.domain.Coupon
import com.example.concurrencycoupon.repository.CouponCountRepository
import com.example.concurrencycoupon.repository.CouponRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ApplyServiceV2(
    private val couponRepository: CouponRepository,
    private val couponCountRepository: CouponCountRepository
) {
    @Transactional
    fun apply(userId: Long) {
        val count = couponCountRepository.count()
        if (count > 100) {
            return
        }
        couponRepository.save(Coupon(userId))
    }
}

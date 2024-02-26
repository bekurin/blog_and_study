package com.example.api.service

import com.example.api.repository.CouponCountRepository
import com.example.api.repository.CouponCreateProducer
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ApplyServiceV3(
    private val couponCountRepository: CouponCountRepository,
    private val couponCreateProducer: CouponCreateProducer
) {
    @Transactional
    fun apply(userId: Long) {
        val count = couponCountRepository.count()
        if (count > 100) {
            return
        }
        couponCreateProducer.create(userId)
    }
}

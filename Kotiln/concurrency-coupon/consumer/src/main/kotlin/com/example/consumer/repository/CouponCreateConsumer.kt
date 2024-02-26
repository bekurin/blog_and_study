package com.example.consumer.repository

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class CouponCreateConsumer {

    @KafkaListener(topics = ["coupon_create"], groupId = "group_1")
    fun listener(userId: Long) {
        // TODO: 쿠폰 저장하기
        println("userID: $userId")
    }
}

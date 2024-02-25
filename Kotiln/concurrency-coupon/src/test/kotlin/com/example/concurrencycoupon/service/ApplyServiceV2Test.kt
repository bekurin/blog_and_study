package com.example.concurrencycoupon.service

import com.example.concurrencycoupon.repository.CouponRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
class ApplyServiceV2Test {

    @Autowired
    private lateinit var applyServiceV2: ApplyServiceV2

    @Autowired
    private lateinit var couponRepository: CouponRepository

    @Test
    fun `1명이 쿠폰 획득을 요청하는 경우`() {
        // given
        val userId = 100L

        // when
        applyServiceV2.apply(userId)

        // then
        val count = couponRepository.count()
        Assertions.assertThat(count).isOne()
    }

    @Test
    fun `다수가 쿠폰 획득을 요청하는 경우`() {
        // given
        val threadCount = 1000
        val executorService = Executors.newFixedThreadPool(32)
        val latch = CountDownLatch(threadCount)

        // when
        repeat((0..threadCount).count()) { userId ->
            executorService.submit {
                try {
                    applyServiceV2.apply(userId.toLong())
                } finally {
                    latch.countDown()
                }
            }
        }
        latch.await()

        // then
        val count = couponRepository.count()
        Assertions.assertThat(count).isEqualTo(100)
    }
}

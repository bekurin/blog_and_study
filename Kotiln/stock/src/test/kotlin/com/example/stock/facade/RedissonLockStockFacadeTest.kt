package com.example.stock.facade

import com.example.stock.config.RepositoryTestBase
import com.example.stock.domain.Stock
import com.example.stock.exception.ClientBadRequestException
import com.example.stock.repository.StockRepository
import com.example.stock.support.ErrorCode
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

class RedissonLockStockFacadeTest : RepositoryTestBase() {
    @Autowired
    private lateinit var redissonLockStockFacade: RedissonLockStockFacade

    @Autowired
    private lateinit var stockRepository: StockRepository

    @AfterEach
    fun cleanUp() {
        stockRepository.deleteAll()
    }

    @Test
    fun `재고는 감소될 수 있다`() {
        // given
        val (productId, quantity, decreaseQuantity) = Triple(1L, 100L, 20L)
        val stock = Stock(productId, quantity)
        stockRepository.saveAndFlush(stock)

        // when
        redissonLockStockFacade.decrease(stock.id, decreaseQuantity)

        // then
        val foundStock = stockRepository.findById(stock.id).get()
        SoftAssertions.assertSoftly { softly ->
            softly.assertThat(foundStock.quantity).isEqualTo(quantity - decreaseQuantity)
            softly.assertThat(foundStock.productId).isEqualTo(productId)
        }
    }

    @Test
    fun `남은 재고보다 많이 감소될 수 없다`() {
        // given
        val (productId, quantity, decreaseQuantity) = Triple(2L, 20L, 100L)
        val stock = Stock(productId, quantity)
        stockRepository.saveAndFlush(stock)

        // when & then
        assertThrows<ClientBadRequestException> {
            redissonLockStockFacade.decrease(stock.id, decreaseQuantity)
        }
            .also { throwable ->
                assertThat(throwable.errorCode).isEqualTo(ErrorCode.NOT_ENOUGH_QUANTITY)
            }
    }

    @Test
    fun `동시에 여러 명이 주문해도 동시성 이슈가 발생하지 않는다`() {
        // given
        val (productId, quantity) = Pair(3L, 100L)
        val stock = Stock(productId, quantity)
        stockRepository.saveAndFlush(stock)

        val nThreads = 32
        val executorService = Executors.newFixedThreadPool(nThreads)
        val latch = CountDownLatch(quantity.toInt())

        // when
        repeat((1..quantity).count()) {
            executorService.submit {
                try {
                    redissonLockStockFacade.decrease(stock.id, 1)
                } finally {
                    latch.countDown()
                }
            }
        }
        latch.await()

        // then
        val foundStock = stockRepository.findById(stock.id).get()
        SoftAssertions.assertSoftly { softly ->
            softly.assertThat(foundStock.quantity).isZero
        }
    }
}


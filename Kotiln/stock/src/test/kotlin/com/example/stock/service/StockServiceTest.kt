package com.example.stock.service

import com.example.stock.config.IntegrationTestBase
import com.example.stock.domain.Stock
import com.example.stock.exception.ClientBadRequestException
import com.example.stock.repository.StockRepository
import com.example.stock.support.ErrorCode
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

class StockServiceTest : IntegrationTestBase() {

    @Autowired
    private lateinit var stockService: StockService

    @Autowired
    private lateinit var stockRepository: StockRepository

    @BeforeEach
    fun cleanUp() {
        stockRepository.deleteAll()
    }

    @Test
    fun `재고는 감소될 수 있다`() {
        // given
        val (productId, quantity, decreaseQuantity) = Triple(1L, 100L, 20L)
        val stock = Stock(productId, quantity)
        stockRepository.save(stock)

        // when
        stockService.decrease(stock.id, decreaseQuantity)

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
        stockRepository.save(stock)

        // when & then
        assertThrows<ClientBadRequestException> {
            stockService.decrease(stock.id, decreaseQuantity)
        }
            .also { throwable ->
                assertThat(throwable.errorCode).isEqualTo(ErrorCode.NOT_ENOUGH_QUANTITY)
            }
    }

    @Test
    fun `동시에 여러 명이 주문하면 동시성 이슈가 발생한다`() {
        // given
        val (productId, quantity) = Pair(3L, 100L)
        val stock = Stock(productId, quantity)
        stockRepository.save(stock)

        val (threadCount, nThreads) = Pair(100, 32)
        val executorService = Executors.newFixedThreadPool(nThreads)
        val latch = CountDownLatch(threadCount)

        // when
        for (i in 1..threadCount) {
            executorService.submit {
                try {
                    stockService.decrease(stock.id, 1)
                } finally {
                    latch.countDown()
                }
            }
        }
        latch.await()

        // then
        val foundStock = stockRepository.findById(productId).get()
        SoftAssertions.assertSoftly { softly ->
            softly.assertThat(foundStock.quantity).isNotZero
        }

    }
}

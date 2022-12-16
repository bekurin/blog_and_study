package chapter03

import chapter03.Listing6.Delivery
import chapter03.Listing6.DeliveryService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.time.LocalDateTime

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class Listing6Test {

    @ParameterizedTest
    @CsvSource("-1, false", "0, false", "1, false", "3, true")
    fun `detects_an_invalid_delivery_date`(daysFromNow: Long, expected: Boolean) {
        //given
        val sut = DeliveryService()
        val deliveryDate = LocalDateTime.now().plusDays(daysFromNow)
        val delivery = Delivery(deliveryDate)

        //when
        val isValid = sut.isDeliveryValid(delivery)

        //then
        assertEquals(isValid, expected)
    }

    @ParameterizedTest
    @ValueSource(longs = [-1, 0, 1])
    fun `detects_an_invalid_delivery_date2`(daysFromNow: Long) {
        //given
        val sut = DeliveryService()
        val deliveryDate = LocalDateTime.now().plusDays(daysFromNow)
        val delivery = Delivery(deliveryDate)

        //when
        val isValid = sut.isDeliveryValid(delivery)

        //then
        assertFalse(isValid)
    }

    @Test
    fun `the_soonest_delivery_date_is_over_two_days_from_now`() {
        //given
        val sut = DeliveryService()
        val deliveryDate = LocalDateTime.now().plusDays(3)
        val delivery = Delivery(deliveryDate)

        //when
        val isValid = sut.isDeliveryValid(delivery)

        //then
        assertTrue(isValid)
    }

    @ParameterizedTest
    @MethodSource("getDateList")
    fun `detects_an_invalid_delivery_date3`(
        deliveryDate: LocalDateTime,
        expected: Boolean
    ) {
        //given
        val sut = DeliveryService()
        val delivery = Delivery(deliveryDate)

        //when
        val isValid = sut.isDeliveryValid(delivery)

        //then
        assertEquals(isValid, expected)
    }

    private fun getDateList(): List<Arguments> {
        return listOf(
            Arguments.arguments(LocalDateTime.now().plusDays(-1), false),
            Arguments.arguments(LocalDateTime.now(), false),
            Arguments.arguments(LocalDateTime.now().plusDays(1), false),
            Arguments.arguments(LocalDateTime.now().plusDays(3), true)
        )
    }
}
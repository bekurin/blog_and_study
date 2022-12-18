package chapter05

import chapter05.Listing1.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.mockito.internal.verification.Times
import kotlin.test.assertEquals

internal class Listing1Test {

    @Test
    fun `sending_a_greeting_email`() {
        //given
        val emailGatewayMock = mock(EmailGateway::class.java)
        val sut = Controller(emailGatewayMock)

        //when
        sut.greetUser("user@gmail.com")

        //then
        verify(emailGatewayMock, Times(1))
            .sendGreetingsEmail("user@gmail.com")
    }

    @Test
    fun `creating_a_report`() {
        //given
        val stub = mock(Database::class.java)
        `when`(stub.getNumberOfUsers()).thenReturn(10)
        val sut = Controller(stub)

        //when
        val report = sut.createReport()

        //then
        assertEquals(10, report.numberOfUsers)
    }
}
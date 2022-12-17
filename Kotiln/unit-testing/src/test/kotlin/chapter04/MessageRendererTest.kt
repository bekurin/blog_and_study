package chapter04

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Test

internal class MessageRendererTest {

    @Test
    fun `rendering_a_message`() {
        //given
        val sut = MessageRenderer()
        val message = Message("h", "b", "i")

        //when
        val html = sut.render(message)

        //then
        assertEquals("<h1>h</h1><b>b</b><i>i</i>", html)
    }

    @Test
    fun `messageRenderer_uess_correct_sub_renderers`() {
        //given
        val sut = MessageRenderer()

        //when
        val renderers = sut.subRenderers

        //then
        assertEquals(3, renderers.size)
        assertInstanceOf(HeaderRenderer::class.java, renderers[0])
        assertInstanceOf(BodyRenderer::class.java, renderers[1])
        assertInstanceOf(FooterRenderer::class.java, renderers[2])
    }
}
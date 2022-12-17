package chapter04

interface Renderer {
    fun render(message: Message): String
}

class MessageRenderer : Renderer {
    var subRenderers: List<Renderer> = listOf(
        HeaderRenderer(),
        BodyRenderer(),
        FooterRenderer()
    )

    override fun render(message: Message): String {
        return subRenderers
            .map { renderer -> renderer.render(message) }
            .reduce { result1, result2 ->
                result1 + result2
            }
    }
}

class FooterRenderer : Renderer {
    override fun render(message: Message): String {
        return "<i>${message.footer}</i>"
    }
}

class BodyRenderer : Renderer {
    override fun render(message: Message): String {
        return "<b>${message.body}</b>"
    }
}

class HeaderRenderer : Renderer {
    override fun render(message: Message): String {
        return "<h1>${message.header}</h1>"
    }
}
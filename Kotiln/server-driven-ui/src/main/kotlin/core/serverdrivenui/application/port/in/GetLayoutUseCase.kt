package core.serverdrivenui.application.port.`in`

import core.serverdrivenui.domain.model.Layout

interface GetLayoutUseCase {
    fun getLayout(): Layout
}

package core.serverdrivenui.application.port.out

import core.serverdrivenui.domain.model.Layout

interface LayoutRepository {
    fun findLayout(): Layout
}

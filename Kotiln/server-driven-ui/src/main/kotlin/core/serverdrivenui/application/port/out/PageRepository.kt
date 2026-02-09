package core.serverdrivenui.application.port.out

import core.serverdrivenui.domain.model.Page

interface PageRepository {
    fun findByPageKey(pageKey: String): Page?
}

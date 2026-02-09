package core.serverdrivenui.application.port.`in`

import core.serverdrivenui.domain.model.Page

interface GetPageUseCase {
    fun getPage(pageKey: String): Page?
}

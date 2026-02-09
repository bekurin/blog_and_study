package core.serverdrivenui.application.service

import core.serverdrivenui.application.port.`in`.GetPageUseCase
import core.serverdrivenui.application.port.out.PageRepository
import core.serverdrivenui.domain.model.Page
import org.springframework.stereotype.Service

@Service
class PageService(
    private val pageRepository: PageRepository,
) : GetPageUseCase {
    override fun getPage(pageKey: String): Page? = pageRepository.findByPageKey(pageKey)
}

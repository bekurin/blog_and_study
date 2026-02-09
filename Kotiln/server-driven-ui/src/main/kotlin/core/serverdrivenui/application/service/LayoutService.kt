package core.serverdrivenui.application.service

import core.serverdrivenui.application.port.`in`.GetLayoutUseCase
import core.serverdrivenui.application.port.out.LayoutRepository
import core.serverdrivenui.domain.model.Layout
import org.springframework.stereotype.Service

@Service
class LayoutService(
    private val layoutRepository: LayoutRepository,
) : GetLayoutUseCase {
    override fun getLayout(): Layout = layoutRepository.findLayout()
}

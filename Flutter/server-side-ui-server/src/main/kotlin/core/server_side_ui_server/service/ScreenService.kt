package core.server_side_ui_server.service

import core.server_side_ui_server.controller.response.AppBarComponent
import core.server_side_ui_server.controller.response.Color
import core.server_side_ui_server.controller.response.ContainerComponent
import core.server_side_ui_server.controller.response.Metadata
import core.server_side_ui_server.controller.response.ScreenResponse
import core.server_side_ui_server.controller.response.TextButtonComponent
import core.server_side_ui_server.controller.response.TextFieldComponent
import core.server_side_ui_server.controller.response.Type
import org.springframework.stereotype.Service

@Service
class ScreenService {
    fun main(): ScreenResponse {
        return ScreenResponse(
            header = AppBarComponent(Type.APP_BAR, "server drive ui 데모"),
            body = TextFieldComponent(Type.TEXT_FILED, "샘플 버튼", false),
            footer = null,
            metadata = Metadata("나야, 들기름")
        )
    }

    fun signIn(): ScreenResponse {
        return ScreenResponse(
            header = AppBarComponent(Type.APP_BAR, "server drive ui 데모"),
            body = ContainerComponent(
                type = Type.CONTAINER,
                padding = "20",
                color = Color("0x192332", "0.3"),
                children = listOf(
                    TextButtonComponent(Type.TEXT_FILED, "메인으로 가 버튼", "/main"),
                )
            ),
            footer = null,
            metadata = Metadata("나야, 들기름2")
        )
    }
}

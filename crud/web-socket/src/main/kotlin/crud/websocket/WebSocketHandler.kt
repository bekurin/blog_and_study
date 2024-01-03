package crud.websocket

import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.ConcurrentHashMap


@Component
class WebSocketHandler : TextWebSocketHandler() {
    private val CLIENTS = ConcurrentHashMap<String, WebSocketSession>()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        println()
        CLIENTS[session.id] = session
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        CLIENTS.remove(session.id)
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val messageReceiver = CLIENTS.filter { entry ->
            entry.key != session.id
        }.takeIf { it.isNotEmpty() } ?: throw RuntimeException("메시지를 전송할 대상이 없습니다.")

        messageReceiver.map { it.value.sendMessage(message) }
    }

}

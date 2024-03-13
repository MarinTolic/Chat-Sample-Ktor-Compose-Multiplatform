package hr.marintolic.chatsample.plugins.websockets

import io.ktor.server.application.*
import io.ktor.server.websocket.*
import java.time.Duration

/**
 * Installs and configures websockets.
 */
internal fun Application.configureWebSocketsPlugin() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(20)
        timeout = Duration.ofSeconds(20)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
}
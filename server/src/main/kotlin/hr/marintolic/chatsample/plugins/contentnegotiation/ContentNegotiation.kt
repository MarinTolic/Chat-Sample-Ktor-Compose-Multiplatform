package hr.marintolic.chatsample.plugins.contentnegotiation

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

/**
 * Configures content negotiation.
 */
internal fun Application.installContentNegotiation() {
    install(ContentNegotiation){
        json()
    }
}
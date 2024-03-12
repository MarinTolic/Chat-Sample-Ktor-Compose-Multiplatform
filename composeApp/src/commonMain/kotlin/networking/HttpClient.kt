package networking

import SERVER_HOST
import SERVER_PORT
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.resources.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*


/**
 * The HttpClient used for network calls.
 */
val httpClient by lazy(::createHttpClient)

/**
 * Creates and sets up an HttpClient.
 *
 * @return An instance of the HttpClient.
 */
private fun createHttpClient(): HttpClient = HttpClient {
    install(Resources)
    install(DefaultRequest) {
        contentType(ContentType.Application.Json)
        url("http://$SERVER_HOST:$SERVER_PORT")
    }
    install(ContentNegotiation) {
        json()
    }
}


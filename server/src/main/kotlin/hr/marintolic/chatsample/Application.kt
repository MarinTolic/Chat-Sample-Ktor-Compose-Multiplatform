package hr.marintolic.chatsample

import SERVER_HOST
import SERVER_PORT
import hr.marintolic.chatsample.database.user.UserDatabase
import hr.marintolic.chatsample.plugins.auth.installAuthenticationPlugin
import hr.marintolic.chatsample.plugins.contentnegotiation.installContentNegotiation
import hr.marintolic.chatsample.plugins.routing.routingModule
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

/**
 * The main entry point of the server application.
 */
fun main() {
    embeddedServer(
        factory = Netty,
        port = SERVER_PORT,
        host = SERVER_HOST,
        module = Application::root
    ).start(wait = true)
}

/**
 * The root function of the application, contains all other modules and performs the installation of plugins.
 */
private fun Application.root() {
    // Initialize the user database
    initUserDatabase()
    // Install plugins first
    installPlugins()
    // Call root module
    rootModule()
}

/**
 * The root module containing all other submodules.
 */
private fun Application.rootModule() {
    routingModule()
}

/**
 * Installs necessary plugins.
 */
private fun Application.installPlugins() {
    installAuthenticationPlugin()
    installContentNegotiation()
}

/**
 * Initializes the user database.
 */
private fun initUserDatabase() {
    UserDatabase.initialize()
}
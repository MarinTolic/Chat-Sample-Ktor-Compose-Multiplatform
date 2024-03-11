package hr.marintolic.chatsample.plugins.routing

import io.ktor.server.application.*

/**
 * The root level routing module containing submodules both for authorized and unauthorized users.
 */
internal fun Application.routingModule(){
    routingUnauthorized()
    routingAuthorizedUsers()
}
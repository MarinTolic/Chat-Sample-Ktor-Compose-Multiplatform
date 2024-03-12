package networking.resources

import io.ktor.resources.*

@Resource(path = "/")
class ServerResources {

    @Resource(path = "login")
    class Login

    @Resource(path = "authorized")
    class Authorized {
        @Resource(path = "chat")
        class Chat(val parent: Authorized = Authorized())
    }
}
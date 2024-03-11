package networking.resources

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/")
class ServerResources {

    @Serializable
    @Resource("login")
    class Login
}
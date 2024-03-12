package networking

import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*

/**
 * A convenience method used to deserialize an HttpResponse body.
 *
 * @receiver Ktor's HttpResponse.
 *
 * @return The deserialized body wrapped in Kotlin's result class if the attempt to deserialize was
 * successful, a [Throwable] wrapped in a result otherwise
 */
internal suspend inline fun <reified T> HttpResponse.deserializeBody(): Result<T> {
    return try {
        if (this.status == HttpStatusCode.OK) {
            val deserializedBody = this.body<T>()

            Result.success(deserializedBody)
        } else {
            Result.failure(Throwable(message = this.status.description))
        }
    } catch (exception: Exception) {
        Result.failure(exception)
    }
}
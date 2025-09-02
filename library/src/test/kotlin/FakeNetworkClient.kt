
import network.HttpResult
import network.NetworkClient
/**
 * Fake network client for testing.
 * Returns preconfigured results based on the URL passed to `get()`.
 */
class FakeNetworkClient : NetworkClient {

    private val responses = mutableMapOf<String, HttpResult>()

    /**
     * Configure the response to return for a given URL.
     */
    fun enqueueResponse(url: String, result: HttpResult) {
        responses[url] = result
    }

    override fun get(url: String): HttpResult {
        // Return the configured response, or a default error if not set
        return responses[url] ?: HttpResult.Error(
            code = 404,
            exception = Throwable("No response configured for URL: $url")
        )
    }
}

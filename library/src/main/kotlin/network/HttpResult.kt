package network

/**
 * Represents the result of an HTTP operation.
 *
 * This is a sealed class, which means it can only be one of the defined subtypes:
 * [Success] or [Error]. This design allows for exhaustive handling of both possible
 * outcomes of a network request in a type-safe way, typically within a `when` expression.
 */
sealed class HttpResult {

    /**
     * Represents a successful HTTP response.
     *
     * This class encapsulates the data received from a successful network request,
     * guaranteeing that both a status code and a response body are present.
     *
     * @property statusCode The standard HTTP status code from the server (e.g., 200 OK, 201 Created).
     * @property body The payload of the response, typically a JSON string or other text-based format.
     */
    data class Success(val statusCode: Int, val body: String) : HttpResult()

    /**
     * Represents a failed HTTP operation.
     *
     * This can be due to a server-side error (indicated by an HTTP status code)
     * or a client-side issue (like a network failure or a parsing error) where no
     * response was received from the server.
     *
     * @property code The HTTP status code from the server if the request was completed but resulted
     * in an error (e.g., 404 Not Found, 500 Internal Server Error). This is nullable
     * because a client-side error (e.g., no network connectivity) may occur before
     * a response is ever received from the server, hence no status code is available.
     * @property exception The underlying [Throwable] that caused the failure. This provides
     * critical diagnostic information, such as [java.net.SocketTimeoutException] or
     * [java.io.IOException]. It can be null, but it's highly recommended
     * to provide it for effective debugging.
     */
    data class Error(val code: Int? = null, val exception: Throwable? = null) : HttpResult()
}

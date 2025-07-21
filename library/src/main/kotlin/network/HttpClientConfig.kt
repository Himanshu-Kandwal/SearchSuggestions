package network

data class HttpClientConfig(
    val connectTimeoutMillis: Int = Constants.connectTimeoutMillis,
    val readTimeoutMillis: Int = Constants.readTimeoutMillis,
    val userAgent: String = Constants.DEFAULT_USER_AGENT
)

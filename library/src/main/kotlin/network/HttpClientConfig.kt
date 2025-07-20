package network

data class HttpClientConfig(
    val connectTimeoutMillis: Int = 1500,
    val readTimeoutMillis: Int = 1000,
    val userAgent: String = Constants.DEFAULT_USER_AGENT
)

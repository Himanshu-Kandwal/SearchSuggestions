package network

interface NetworkClient {
    fun get(url: String): HttpResult
}

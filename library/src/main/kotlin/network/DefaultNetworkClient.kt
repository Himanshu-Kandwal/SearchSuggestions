package network

import java.net.HttpURLConnection
import java.net.URI

/**
 * Default implementation of [NetworkClient] to handle network calls
 */
class DefaultNetworkClient(private val httpClientConfig: HttpClientConfig) : NetworkClient {

    override fun get(url: String): HttpResult {
        val queryURL = URI(url).toURL()
        val connection = queryURL.openConnection() as HttpURLConnection
        return try {
            connection.requestMethod = "GET"
            connection.connectTimeout = httpClientConfig.connectTimeoutMillis
            connection.readTimeout = httpClientConfig.readTimeoutMillis
            connection.setRequestProperty(
                "User-Agent", httpClientConfig.userAgent
            )
            connection.connect()
            connection.inputStream.bufferedReader().use {
                HttpResult.Success(statusCode = connection.responseCode, body = it.readText())
            }
        } catch (exception: Exception) {
            val responseCode = try {
                if (connection.responseCode == -1) null else connection.responseCode
            } catch (_: Exception) {
                null
            }
            return HttpResult.Error(responseCode, exception)
        } finally {
            connection.disconnect()
        }
    }
}
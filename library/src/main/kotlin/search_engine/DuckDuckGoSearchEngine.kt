package search_engine

import network.HttpResult
import network.NetworkClient
import org.example.searchsuggestion.engine.SearchEngine
import parser.SuggestionsParser
import java.net.URLEncoder

class DuckDuckGoSearchEngine(
    private val networkClient: NetworkClient, private val parser: SuggestionsParser
) : SearchEngine {

    companion object {
        const val QUERY_URL = "https://duckduckgo.com/ac/?q=%s&type=list"
    }

    override fun getSearchSuggestions(query: String, maxSuggestions: Int): List<String> {
        val encodedQuery = URLEncoder.encode(query, "UTF-8")
        val queryUrl = String.format(QUERY_URL, encodedQuery)
        return when (val response = networkClient.get(queryUrl)) {
            is HttpResult.Error -> emptyList()
            is HttpResult.Success -> {
                parser.parse(response.body, maxSuggestions)
            }
        }
    }
}

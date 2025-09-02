package search_engine

import network.HttpResult
import network.NetworkClient
import org.example.searchsuggestion.engine.SearchEngine
import parser.GoogleSuggestionsParser
import parser.SuggestionsParser
import java.net.URLEncoder

class GoogleSearchEngine(
    private val networkClient: NetworkClient, private val parser: SuggestionsParser = GoogleSuggestionsParser()
) : SearchEngine {

    companion object {
        const val QUERY_URL = "https://google.com/complete/search?client=firefox&q=%s"
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

package core

import network.DefaultNetworkClient
import network.HttpClientConfig
import org.example.searchsuggestion.core.SuggestionRequest
import org.example.searchsuggestion.engine.SearchEngine
import parser.DuckDuckGoSuggestionsParser
import search_engine.DuckDuckGoSearchEngine
import search_engine.SearchEngineType

interface SuggestionService {
    fun getSuggestions(suggestionRequest: SuggestionRequest): List<String>

    companion object {

        fun getDefault(): SuggestionService {
            return DefaultSuggestionService(
                DuckDuckGoSearchEngine(
                    DefaultNetworkClient(
                        HttpClientConfig()
                    ), DuckDuckGoSuggestionsParser()
                )
            )
        }

        fun create(searchEngine: SearchEngine): SuggestionService {
            return DefaultSuggestionService(
                searchEngine
            )
        }

        fun create(searchEngineType: SearchEngineType, httpClientConfig: HttpClientConfig): SuggestionService {
            when (searchEngineType) {
                SearchEngineType.DUCKDUCKGO -> return DefaultSuggestionService(
                    DuckDuckGoSearchEngine(
                        DefaultNetworkClient(httpClientConfig),
                        DuckDuckGoSuggestionsParser()
                    )
                )
                /* SearchEngineType.GOOGLE -> TODO()
                 SearchEngineType.BING -> TODO()
                 SearchEngineType.YAHOO -> TODO()*/
            }
        }

        fun createWithCaching(): SuggestionService {
            return createWithCaching(SearchEngineType.DUCKDUCKGO, HttpClientConfig())
        }

        fun createWithCaching(searchEngine: SearchEngine): SuggestionService {
            return CachingSuggestionService(searchEngine)
        }

        fun createWithCaching(
            searchEngineType: SearchEngineType,
            httpClientConfig: HttpClientConfig
        ): SuggestionService {
            when (searchEngineType) {
                SearchEngineType.DUCKDUCKGO -> return CachingSuggestionService(
                    DuckDuckGoSearchEngine(
                        DefaultNetworkClient(
                            httpClientConfig
                        ), DuckDuckGoSuggestionsParser()
                    )
                )
            }
        }
    }
}
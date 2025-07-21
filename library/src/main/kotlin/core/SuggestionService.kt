package core

import network.DefaultNetworkClient
import network.HttpClientConfig
import org.example.searchsuggestion.core.SuggestionRequest
import parser.DuckDuckGoSuggestionsParser
import search_engine.DuckDuckGoSearchEngine

interface SuggestionService {
    fun getSuggestions(suggestionRequest: SuggestionRequest): List<String>

    companion object {
        fun getInstance(): SuggestionService {
            return DefaultSuggestionService(
                DuckDuckGoSearchEngine(
                    DefaultNetworkClient(
                        HttpClientConfig()
                    ), DuckDuckGoSuggestionsParser()
                )
            )
        }
    }
}
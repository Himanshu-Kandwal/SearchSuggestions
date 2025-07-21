package core

import org.example.searchsuggestion.core.SuggestionRequest
import org.example.searchsuggestion.engine.SearchEngine

class DefaultSuggestionService(private val searchEngine: SearchEngine) : SuggestionService {
    override fun getSuggestions(suggestionRequest: SuggestionRequest): List<String> {
        return searchEngine.getSearchSuggestions(suggestionRequest.query, suggestionRequest.maxResults)
    }
}

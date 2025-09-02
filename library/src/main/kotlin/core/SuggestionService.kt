package core

import org.example.searchsuggestion.core.SuggestionRequest

interface SuggestionService {
    fun getSuggestions(suggestionRequest: SuggestionRequest): List<String>
}
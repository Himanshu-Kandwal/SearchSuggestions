package org.example.searchsuggestion.core

import org.example.searchsuggestion.engine.SearchEngine

data class SuggestionRequest(
    val query: String,
    val maxResults: Int
)
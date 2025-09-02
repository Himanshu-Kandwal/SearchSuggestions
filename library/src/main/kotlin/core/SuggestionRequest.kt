package org.example.searchsuggestion.core

data class SuggestionRequest(
    val query: String,
    val maxResults: Int
)
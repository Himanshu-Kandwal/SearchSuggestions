package org.example.searchsuggestion.engine

interface SearchEngine {
    fun getSearchSuggestions(query: String, maxSuggestions: Int): List<String>
}
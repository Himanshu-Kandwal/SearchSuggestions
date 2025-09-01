package core

import cache.CacheConfig
import cache.CacheItem
import cache.SuggestionCache
import cache.InMemoryTTLLRUCache
import org.example.searchsuggestion.core.SuggestionRequest
import org.example.searchsuggestion.engine.SearchEngine

class CachingSuggestionService(
    private val searchEngine: SearchEngine,
    private val cache: SuggestionCache<String, CacheItem> = InMemoryTTLLRUCache(CacheConfig()),
) : SuggestionService {

    override fun getSuggestions(suggestionRequest: SuggestionRequest): List<String> {
        if (cache.contains(suggestionRequest.query)) return cache.get(suggestionRequest.query)!!.suggestionsList
        val suggestionsList = searchEngine.getSearchSuggestions(suggestionRequest.query, suggestionRequest.maxResults)
        cache.put(suggestionRequest.query, CacheItem(suggestionsList))
        return suggestionsList
    }
}

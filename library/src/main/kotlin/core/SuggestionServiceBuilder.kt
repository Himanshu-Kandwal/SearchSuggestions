package core

import cache.CacheConfig
import cache.InMemoryTTLLRUCache
import cache.SuggestionCache
import network.DefaultNetworkClient
import network.HttpClientConfig
import network.NetworkClient
import org.example.searchsuggestion.engine.SearchEngine
import parser.DuckDuckGoSuggestionsParser
import parser.GoogleSuggestionsParser
import search_engine.DuckDuckGoSearchEngine
import search_engine.GoogleSearchEngine
import search_engine.SearchEngineType

class SuggestionServiceBuilder {
    private var httpClientConfig: HttpClientConfig = HttpClientConfig()
    private var networkClient: NetworkClient? = null
    private var suggestionCache: SuggestionCache? = null
    private var searchEngine: SearchEngine? = null
    private var searchEngineType: SearchEngineType = SearchEngineType.DUCKDUCKGO
    private var cacheConfig: CacheConfig? = null

    fun withNetworkClient(networkClient: NetworkClient): SuggestionServiceBuilder = apply {
        this.networkClient = networkClient
    }

    fun withCaching(cacheConfig: CacheConfig = CacheConfig()): SuggestionServiceBuilder = apply {
        this.cacheConfig = cacheConfig
    }

    fun withCustomCache(suggestionCache: SuggestionCache): SuggestionServiceBuilder = apply {
        this.suggestionCache = suggestionCache
    }

    fun withSearchEngine(searchEngine: SearchEngine): SuggestionServiceBuilder = apply {
        this.searchEngine = searchEngine
    }

    fun withSearchEngine(searchEngineType: SearchEngineType): SuggestionServiceBuilder = apply {
        this.searchEngineType = searchEngineType
    }

    fun build(): SuggestionService {
        val finalNetworkClient = networkClient ?: DefaultNetworkClient(httpClientConfig)

        val finalSearchEngine = searchEngine ?: when (searchEngineType) {
            SearchEngineType.GOOGLE -> GoogleSearchEngine(finalNetworkClient, GoogleSuggestionsParser())
            SearchEngineType.DUCKDUCKGO -> DuckDuckGoSearchEngine(finalNetworkClient, DuckDuckGoSuggestionsParser())
        }

        if (suggestionCache != null || cacheConfig != null) {
            val finalSuggestionCache = suggestionCache
                ?: InMemoryTTLLRUCache(cacheConfig ?: CacheConfig())
            return CachingSuggestionService(finalSearchEngine, finalSuggestionCache)
        } else {
            return DefaultSuggestionService(finalSearchEngine)
        }

    }
}
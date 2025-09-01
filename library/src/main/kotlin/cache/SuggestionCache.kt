package cache


interface SuggestionCache {
    fun get(key: String): CacheItem?
    fun put(key: String, suggestions: CacheItem)
    fun delete(key: String): String?
    fun contains(key: String): Boolean
}
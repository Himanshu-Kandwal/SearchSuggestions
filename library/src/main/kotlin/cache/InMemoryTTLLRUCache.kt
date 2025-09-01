package cache

class InMemoryTTLLRUCache(private val cacheConfig: CacheConfig = CacheConfig()) : SuggestionCache {

    private val cache = object : LinkedHashMap<String, CacheItem>(cacheConfig.cacheSize, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, CacheItem>?): Boolean { //remove the eldest entry when this cache size is bigger than configured cache size
            return size > cacheConfig.cacheSize
        }
    }

    @Synchronized
    override fun get(key: String): CacheItem? {
        val item = cache[key]
        item?.let {
            if (isExpired(it)) {
                delete(key)
                return null
            }
            else return it
        }
        return null
    }

    @Synchronized
    override fun put(key: String, suggestions: CacheItem) {
        cache[key] = suggestions
    }

    @Synchronized
    override fun delete(key: String): String? {
        return if (cache.remove(key) != null) key else null
    }

    @Synchronized
    override fun contains(key: String): Boolean {
        return cache.containsKey(key)
    }

    private fun isExpired(item: CacheItem): Boolean {
        val now = System.currentTimeMillis()
        return (now - item.insertedAt) > cacheConfig.cacheTTL
    }
}
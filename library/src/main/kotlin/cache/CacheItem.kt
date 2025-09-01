package cache

data class CacheItem(val suggestionsList: List<String>, val insertedAt: Long = System.currentTimeMillis())

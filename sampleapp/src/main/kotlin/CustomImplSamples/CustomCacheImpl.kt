package org.hk.CustomImplSamples

import cache.CacheItemimport cache.SuggestionCache

class CustomCacheImpl : SuggestionCache {
    override fun contains(key: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun delete(key: String): String? {
        TODO("Not yet implemented")
    }

    override fun get(key: String): CacheItem? {
        TODO("Not yet implemented")
    }

    override fun put(key: String, suggestions: CacheItem) {
        TODO("Not yet implemented")
    }
}
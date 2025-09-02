package cache

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class InMemoryTTLLRUCacheTest {

    private lateinit var cache: InMemoryTTLLRUCache
    private val cacheSize = 3
    private val cacheTTL = 1000L // 1 second TTL for testing

    @BeforeEach
    fun setup() {
        val config = CacheConfig(cacheSize = cacheSize, cacheTTL = cacheTTL)
        cache = InMemoryTTLLRUCache(config)
    }

    @Test
    fun testPutAndGet() {
        val item = CacheItem(listOf("suggestion1"))
        cache.put("key1", item)

        val cachedItem = cache.get("key1")
        assertNotNull(cachedItem)
        assertEquals(item.suggestionsList, cachedItem?.suggestionsList)
    }

    @Test
    fun testContains() {
        val item = CacheItem(listOf("suggestion2"))
        cache.put("key2", item)

        assertTrue(cache.contains("key2"))
        assertFalse(cache.contains("nonexistent"))
    }

    @Test
    fun testDelete() {
        val item = CacheItem(listOf("suggestion3"))
        cache.put("key3", item)

        val deletedKey = cache.delete("key3")
        assertEquals("key3", deletedKey)
        assertFalse(cache.contains("key3"))

        val nonExistentDelete = cache.delete("key3")
        assertNull(nonExistentDelete)
    }

    @Test
    fun testExpiration() {
        val item = CacheItem(listOf("suggestion4"))
        cache.put("key4", item)

        Thread.sleep(cacheTTL + 100)

        val expiredItem = cache.get("key4")
        assertNull(expiredItem)
        assertFalse(cache.contains("key4"))
    }

    @Test
    fun testLRURemoval() {
        cache.put("k1", CacheItem(listOf("d1")))
        cache.put("k2", CacheItem(listOf("d2")))
        cache.put("k3", CacheItem(listOf("d3")))

        // Access k1 to make it recently used
        cache.get("k1")

        // Add new item, should evict LRU (k2)
        cache.put("k4", CacheItem(listOf("d4")))

        assertTrue(cache.contains("k1"))
        assertFalse(cache.contains("k2"))
        assertTrue(cache.contains("k3"))
        assertTrue(cache.contains("k4"))
    }
}

package cache

import kotlin.time.Duration.Companion.minutes

data class CacheConfig(val cacheSize: Int = 100, val cacheTTL: Long = 5.minutes.inWholeMilliseconds)
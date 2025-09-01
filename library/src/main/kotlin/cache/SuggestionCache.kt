package cache


interface SuggestionCache<K, V> {
    fun get(key: K): V?
    fun put(key: K, suggestions: V)
    fun delete(key: K): K?
    fun contains(key: K): Boolean
}
# Realtime Search Suggestions Library

A robust, extensible Kotlin library for fetching and caching search suggestions from multiple search engines (Google, DuckDuckGo). Designed for high performance, testability, and easy integration into JVM applications.

---

## Features

- **Multi-Engine Support**: Pluggable architecture supporting Google and DuckDuckGo out of the box.
- **Unified API**: Single interface for requesting suggestions, regardless of the underlying search engine.
- **Configurable & Customizable Caching**: In-built In-memory TTL+LRU cache or plug in Custom Cache to minimize network calls and improve performance.
- **Customizable Networking**: Easily swap or configure the HTTP client.
- **Extensible Parsing**: Modular suggestion parsers for different search engine response formats.
- **Test Coverage**: Includes comprehensive unit tests for core components and parsers.

---

## Use Cases

- **Autocomplete**: Power search/autocomplete bars in desktop, web, or mobile apps.
- **Data Enrichment**: Fetch trending or related search queries for analytics or content recommendation.
- **Research Tools**: Integrate with tools that analyze or visualize search trends.
- **Custom Browsers**: Quickly add suggestion capabilities to search bars.

---

## Requirements

- **Internet connectivity** (necessary to fetch live suggestions from Google or DuckDuckGo).
- JVM 21+ and Kotlin 2.0+

---

## How it Works

The library fetches search suggestions by:

1. Sending a **network request** to the chosen search engine (Google or DuckDuckGo).
2. Receiving the response JSON or plain text.
3. **Parsing** the response using the respective `SuggestionsParser` implementation.
4. **Returning a list of suggestions**.
5. Optionally, storing results in an **in-memory TTL+LRU cache** to reduce redundant network calls.

All of this is configurable through the `SuggestionServiceBuilder`.

---

## Getting Started

### 1. Add as a Dependency

```kotlin
// Add the JitPack repository
repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

// Add the library dependency
dependencies {
    implementation("com.github.Himanshu-Kandwal.SearchSuggestions:library:v1.0.1") //use latest 
}
```

### 2. Configure the Service

```kotlin
val suggestionService = SuggestionServiceBuilder()
    .withCaching()
    .withSearchEngine(SearchEngineType.DUCKDUCKGO)
    .build() // customize as per requirements
```

### 3. Basic Usage

#### Fetching Suggestions (Default: DuckDuckGo)

```kotlin
import core.SuggestionServiceBuilder
import org.example.searchsuggestion.core.SuggestionRequest

val suggestionService = SuggestionServiceBuilder().build()
val suggestions = suggestionService.getSuggestions(SuggestionRequest("apple", 5))
println(suggestions)
```

#### Using Google as the Search Engine

```kotlin
import core.SuggestionServiceBuilder
import search_engine.SearchEngineType
import org.example.searchsuggestion.core.SuggestionRequest

val googleService = SuggestionServiceBuilder()
    .withSearchEngine(SearchEngineType.GOOGLE)
    .build()

val suggestions = googleService.getSuggestions(SuggestionRequest("banana", 5))
println(suggestions)
```

#### Enabling Caching

```kotlin
import core.SuggestionServiceBuilder
import org.example.searchsuggestion.core.SuggestionRequest

val cachedService = SuggestionServiceBuilder()
    .withCaching() // Uses default cache config (TTL, size)
    .build()

val suggestions = cachedService.getSuggestions(SuggestionRequest("mango", 5))
println(suggestions)
```

#### Custom Cache Configuration

```kotlin
import core.SuggestionServiceBuilder
import cache.CacheConfig
import org.example.searchsuggestion.core.SuggestionRequest

val customCacheConfig = CacheConfig(cacheSize = 200, cacheTTL = 10 * 60 * 1000) // 10 minutes
val service = SuggestionServiceBuilder()
    .withCaching(customCacheConfig)
    .build()
```

#### Custom Network Client

```kotlin
import core.SuggestionServiceBuilder
import network.DefaultNetworkClient
import network.HttpClientConfig

val customNetworkClient = DefaultNetworkClient(HttpClientConfig(connectTimeoutMillis = 3000))
val service = SuggestionServiceBuilder()
    .withNetworkClient(customNetworkClient)
    .build()
```

---

## API Overview

- [`SuggestionService`](src/main/kotlin/core/SuggestionService.kt): Main interface for fetching suggestions.
- [`SuggestionServiceBuilder`](src/main/kotlin/core/SuggestionServiceBuilder.kt): Fluent builder for configuring the service.
- [`SuggestionRequest`](src/main/kotlin/core/SuggestionRequest.kt): Data class for query and result count.
- [`SearchEngineType`](src/main/kotlin/search_engine/SearchEngineType.kt): Enum for supported engines.
- [`CacheConfig`](src/main/kotlin/cache/CacheConfig.kt): Cache size and TTL configuration.

---

## Extending

- **Add a new search engine**: Implement [`SearchEngine`](src/main/kotlin/search_engine/SearchEngine.kt) and plug it into the builder.
- **Custom parser**: Implement [`SuggestionsParser`](src/main/kotlin/parser/SuggestionsParser.kt) for new response formats.
- **Custom cache**: Implement [`SuggestionCache`](src/main/kotlin/cache/SuggestionCache.kt) for distributed or persistent caching.

---

## Contributing

1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/NewFeature`).
3. Commit your changes (`git commit -m 'Add new feature'`).
4. Push to the branch (`git push origin feature/NewFeature`).
5. Create a Pull Request.

---
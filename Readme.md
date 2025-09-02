# Realtime Search Suggestions Library

A robust, extensible Kotlin library for fetching and caching search suggestions from multiple search engines (Google,
DuckDuckGo). Designed for high performance, testability, and easy integration into JVM applications.

---

## Features

- **Simple**: Realtime Search suggestions in just 3 lines of code (without customisation).
- **Multi-Engine Support**: Pluggable architecture supporting Google and DuckDuckGo out of the box.
- **Unified API**: Single interface for requesting suggestions, regardless of the underlying search engine.
- **Configurable & Customizable Caching**: In-built In-memory TTL+LRU cache or plug in Custom Cache to minimize network
  calls and improve performance.
- **Customizable Networking**: Easily swap or configure the HTTP client.
- **Extensible Parsing**: Modular suggestion parsers for different search engine response formats.

[//]: # (- **Test Coverage**: Includes comprehensive unit tests for core components and parsers.)

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

### 2. Using Library

We have options to customise or not.

#### 1. Use without Customizing

```kotlin
fun main() {
    //step 1 - get suggestion service
    val defaultSuggestionService = SuggestionServiceBuilder().build() //returns default/un-customised service

    //step 2 - make suggestion request
    val suggestionRequest = SuggestionRequest("elon musk", 5) //pass query and limit for result

    //step 3 - call getSuggestions() to and pass suggestionRequest

    val suggestionsList = defaultSuggestionService.getSuggestions(suggestionRequest)

    //Optional step 4 - print them
    println(suggestionsList)

    //output = [elon musk, elon musk net worth, elon musk age, elon musk news, elon musk wife]

}
```

#### 1. Use with Customization

```kotlin
fun main() {
    //step 1 - get suggestion service with customisation as per needs e.g caching, search engine etc
    val defaultSuggestionService =
        SuggestionServiceBuilder().withCaching().withSearchEngine(SearchEngineType.GOOGLE).build()
    ...
    //same as above
    ...

    // Output = [elon musk, elon musk net worth, elon musk net worth in rupees, elon musk children, elon musk father]
}
```

## 3. Customization

### Change or Integrated Search Engine

#### In-built search engines:
```kotlin

val googleService = SuggestionServiceBuilder()
    .withSearchEngine(SearchEngineType.GOOGLE) //pass Enum here currently supports Google and DuckDuckGo
    .build()


```

#### Custom search engines:
```kotlin
Note: Custom Search Engine must provide their own Parser i.e implemention of SuggestionsParser interface

//Step 1 - Implement SearchEngine interface

class CustomImplSamples : SearchEngine {
  override fun getSearchSuggestions(query: String, maxSuggestions: Int): List<String> {
    TODO("Not yet implemented")
  }
}

//Step 2 - Pass it to builder
val googleService = SuggestionServiceBuilder()
    .withSearchEngine(CustomImplSamples()) //passing custom search engine's concrete implementation
    .build()


```

#### Enabling Caching

```kotlin

val cachedService = SuggestionServiceBuilder()
    .withCaching() // Uses default cache config (TTL, size)
    .build()

val suggestions = cachedService.getSuggestions(SuggestionRequest("mango", 5))
println(suggestions)
```

### Custom Cache Configuration

#### Custom cache size and duration:
```kotlin

//step 1 - create cache config
val customCacheConfig = CacheConfig(cacheSize = 200, cacheTTL = 10 * 60 * 1000) // 10 minutes

// step 2 - pass cache config here
val service = SuggestionServiceBuilder()
    .withCaching(customCacheConfig) 
    .build()
```
#### Custom cache implementation:
```kotlin
//step 1 - Implement the Suggestion cache interface
class CustomCacheImpl : SuggestionCache {
                ....
  //methods implemented here
                ....
}

// step 2 - pass custom cache impl here
val service = SuggestionServiceBuilder()
  .withCaching(CustomCacheImpl())
  .build()
```

### Customize Networking
We have two ways to customize it,
#### 1. Changing http configuration
Using HttpClientConfig we can pass timeout, user agent etc
```kotlin
// step 1 : create custom http config and pass into Default network client
val customHttpConfig = HttpClientConfig(connectTimeoutMillis = 3000)
val customNetworkClient = DefaultNetworkClient(customHttpConfig) //

//Step 2: Pass customised client to builder
val service = SuggestionServiceBuilder()
    .withNetworkClient(customNetworkClient) //here
    .build()
```

#### 2. Using custom network client 

```kotlin
//Step 1: Implement NetworkClient interface
class CustomNetworkingClientImpl : NetworkClient {
  override fun get(url: String): HttpResult {
    TODO("Not yet implemented")
  }
}

//Step  2: Pass CustomNetworkingClientImpl() to builder
val service = SuggestionServiceBuilder()
  .withNetworkClient(CustomNetworkingClientImpl()) //here
  .build()

```

---

## API Overview

- [`SuggestionService`](src/main/kotlin/core/SuggestionService.kt): Main interface for fetching suggestions.
- [`SuggestionServiceBuilder`](src/main/kotlin/core/SuggestionServiceBuilder.kt): Fluent builder for configuring the
  service.
- [`SuggestionRequest`](src/main/kotlin/core/SuggestionRequest.kt): Data class for query and result count.
- [`SearchEngineType`](src/main/kotlin/search_engine/SearchEngineType.kt): Enum for supported engines.
- [`CacheConfig`](src/main/kotlin/cache/CacheConfig.kt): Cache size and TTL configuration.

---

## Extending

- **Add a new search engine**: Implement [`SearchEngine`](src/main/kotlin/search_engine/SearchEngine.kt) and plug it
  into the builder.
- **Custom parser**: Implement [`SuggestionsParser`](src/main/kotlin/parser/SuggestionsParser.kt) for new response
  formats.
- **Custom cache**: Implement [`SuggestionCache`](src/main/kotlin/cache/SuggestionCache.kt) for distributed or
  persistent caching.

---

## Contributing

1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/NewFeature`).
3. Commit your changes (`git commit -m 'Add new feature'`).
4. Push to the branch (`git push origin feature/NewFeature`).
5. Create a Pull Request.

---
package org.hk

import core.SuggestionServiceBuilder
import network.DefaultNetworkClient
import network.HttpClientConfig
import network.HttpResult
import org.example.searchsuggestion.core.SuggestionRequest
import parser.DuckDuckGoSuggestionsParser
import search_engine.SearchEngineType

val client = DefaultNetworkClient(HttpClientConfig())

fun main() {
    println("Hello World! app")

    //println(client.get("http://google.com/complete/search?output=toolbar&q=apple"))
    /* getDefaultSuggestionService()
     getCustomSuggestionService()*/
    getDefaultSuggestionMultipleSearching()
    getCachedSuggestionService()
}


fun getDefaultCachingSuggestionService() {
    val service = SuggestionServiceBuilder().withCaching().build()
    println(service.getSuggestions(SuggestionRequest("apple", 5)))
}

fun getDefaultSuggestionService() {
    val service = SuggestionServiceBuilder().build()
    println(service.getSuggestions(SuggestionRequest("apple", 5)))
}

fun getDefaultSuggestionMultipleSearching() {
    println("Default Service BULK")

    val service = SuggestionServiceBuilder().build()
    var startTime = System.currentTimeMillis()
    println(service.getSuggestions(SuggestionRequest("Mangoes", 5)))
    var now = System.currentTimeMillis();

    println("First query took :${now - startTime}")


    startTime = System.currentTimeMillis()
    println(service.getSuggestions(SuggestionRequest("Mangoes", 5)))
    println(service.getSuggestions(SuggestionRequest("Mangoes", 5)))
    println(service.getSuggestions(SuggestionRequest("Mangoes", 5)))
    println(service.getSuggestions(SuggestionRequest("Mangoes", 5)))
    println(service.getSuggestions(SuggestionRequest("Mangoes", 5)))
    println(service.getSuggestions(SuggestionRequest("Mangoes", 5)))
    now = System.currentTimeMillis()
    println("next 6 query took :${now - startTime}")
}

fun getCachedSuggestionService() {
    println("Caching Service")

    val service = SuggestionServiceBuilder().withCaching().build()
    var startTime = System.currentTimeMillis()
    println(service.getSuggestions(SuggestionRequest("apple", 5)))
    var now = System.currentTimeMillis();

    println("First query took :${now - startTime}")


    startTime = System.currentTimeMillis()
    println(service.getSuggestions(SuggestionRequest("apple", 5)))
    println(service.getSuggestions(SuggestionRequest("apple", 5)))
    println(service.getSuggestions(SuggestionRequest("apple", 5)))
    println(service.getSuggestions(SuggestionRequest("apple", 5)))
    println(service.getSuggestions(SuggestionRequest("apple", 5)))
    println(service.getSuggestions(SuggestionRequest("apple", 5)))
    now = System.currentTimeMillis()
    println("next 6 query took :${now - startTime}")

}

fun getCustomSuggestionService() {
    val service = SuggestionServiceBuilder().withSearchEngine(SearchEngineType.GOOGLE).build()
    println(service.getSuggestions(SuggestionRequest("mango", 5)))
}

fun logAllResponses() {
    println(client.get("https://google.com/complete/search?client=firefox&q=apple"))
    println(client.get("https://www.bing.com/osjson.aspx?query=applel&language=apple"))
    println(client.get("https://duckduckgo.com/ac/?q=apple&type=list"))
    println(client.get("https://search.yahoo.com/sugg/gossip/gossip-us-ura/?output=sd1&command=apple"))
}

fun getDuckDuckGoSuggestions() {
    val startTime = System.currentTimeMillis()
    when (val response = client.get("https://duckduckgo.com/ac/?q=inDIA&type=list")) {
        is HttpResult.Error -> response.exception?.also {
            throw it
        }

        is HttpResult.Success -> {
            val body = response.body
            val suggestionService = DuckDuckGoSuggestionsParser().parse(body, 5)
            println(suggestionService)
            println(System.currentTimeMillis() - startTime)
        }
    }
}
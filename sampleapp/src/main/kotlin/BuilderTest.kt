package org.hk

import core.SuggestionServiceBuilder
import org.example.searchsuggestion.core.SuggestionRequest
import search_engine.SearchEngineType


fun main() {

    val suggestionService = SuggestionServiceBuilder().build()
    val custom = SuggestionServiceBuilder().withCaching().withSearchEngine(SearchEngineType.GOOGLE).build()
    val google = custom
    /*SuggestionService.create(
        GoogleSearchEngine(
            DefaultNetworkClient(HttpClientConfig()),
            GoogleSuggestionsParser()
        )
    )*/

    val start = System.currentTimeMillis()
    val suggest = google.getSuggestions(SuggestionRequest("LOL", 100))
    val end = System.currentTimeMillis()
    println(suggest)

    println("total time ${end - start}")
    println(google.getSuggestions(SuggestionRequest("BOLLL", 100)))
    println(google.getSuggestions(SuggestionRequest("Doll", 100)))
    println(google.getSuggestions(SuggestionRequest("Jump", 100)))

    val sc = System.currentTimeMillis()
    println(google.getSuggestions(SuggestionRequest("LOL", 100)))
    println(google.getSuggestions(SuggestionRequest("LOL", 100)))
    println(google.getSuggestions(SuggestionRequest("LOL", 100)))
    println(google.getSuggestions(SuggestionRequest("LOL", 100)))
    println(google.getSuggestions(SuggestionRequest("LOL", 100)))
    println(google.getSuggestions(SuggestionRequest("LOL", 100)))
    println(google.getSuggestions(SuggestionRequest("LOL", 100)))
    println(google.getSuggestions(SuggestionRequest("LOL", 100)))
    println(google.getSuggestions(SuggestionRequest("LOL", 100)))
    println(google.getSuggestions(SuggestionRequest("LOL", 100)))
    println(google.getSuggestions(SuggestionRequest("LOL", 100)))
    println(google.getSuggestions(SuggestionRequest("LOL", 100)))
    val eC = System.currentTimeMillis()
    println(eC - sc)


}
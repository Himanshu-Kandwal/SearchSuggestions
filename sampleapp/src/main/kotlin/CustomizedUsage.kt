package org.hk

import core.SuggestionServiceBuilder
import org.example.searchsuggestion.core.SuggestionRequest
import search_engine.SearchEngineType

fun main() {
    //step 1 - get suggestion service with customisation as per needs e.g caching, search engine etc
    val defaultSuggestionService = SuggestionServiceBuilder().withCaching().withSearchEngine(SearchEngineType.GOOGLE).build()

    //step 2 - make suggestion request
    val suggestionRequest = SuggestionRequest("elon musk", 5) //pass query and limit for result

    //step 3 - call getSuggestions() to and pass suggestionRequest

    val suggestionsList = defaultSuggestionService.getSuggestions(suggestionRequest)

    //Optional step 4 - print them
    println(suggestionsList)

    // Output = [elon musk, elon musk net worth, elon musk net worth in rupees, elon musk children, elon musk father]
}
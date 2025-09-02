package org.hk

import core.SuggestionServiceBuilder
import org.example.searchsuggestion.core.SuggestionRequest

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
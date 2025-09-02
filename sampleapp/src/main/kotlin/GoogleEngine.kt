package org.hk

import core.SuggestionService
import core.SuggestionServiceBuilder
import network.DefaultNetworkClient
import network.HttpClientConfig
import org.example.searchsuggestion.core.SuggestionRequest
import parser.DuckDuckGoSuggestionsParser
import parser.GoogleSuggestionsParser
import search_engine.DuckDuckGoSearchEngine
import search_engine.GoogleSearchEngine

fun main() {
    val google = SuggestionServiceBuilder().withSearchEngine(
        GoogleSearchEngine(
            DefaultNetworkClient(HttpClientConfig()),
            GoogleSuggestionsParser()
        )
    ).build()

    val start = System.currentTimeMillis()
    val suggest = google.getSuggestions(
        SuggestionRequest(
            "LOL",
            100
        )
    )
    println(suggest)

    val ddgo = SuggestionServiceBuilder().withSearchEngine(
        DuckDuckGoSearchEngine(
            DefaultNetworkClient(HttpClientConfig()),
            DuckDuckGoSuggestionsParser()
        )
    ).build()

    println(
        ddgo.getSuggestions(
            SuggestionRequest(
                "LOL",
                100
            )
        )
    )
    val end = System.currentTimeMillis()
    println("total time ${end - start}")
    println(suggest)
}
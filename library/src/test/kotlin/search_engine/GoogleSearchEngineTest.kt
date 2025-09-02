package search_engine

import FakeNetworkClient
import network.HttpResult
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import parser.SuggestionsParser

class GoogleSearchEngineTest {

    private lateinit var fakeNetwork: FakeNetworkClient
    private lateinit var searchEngine: GoogleSearchEngine

    @BeforeEach
    fun setup() {
        fakeNetwork = FakeNetworkClient()

        // Simple fake parser for predictable results
        val fakeParser = object : SuggestionsParser {
            override fun parse(response: String, take: Int): List<String> {
                return response.split(",").take(take)
            }
        }

        searchEngine = GoogleSearchEngine(fakeNetwork, fakeParser)
    }

    @Test
    fun `returns parsed suggestions when network call succeeds`() {
        val query = "kotlin"
        val encodedQuery = "kotlin" // deterministic URLEncoder encoding
        val queryUrl = "https://google.com/complete/search?client=firefox&q=$encodedQuery"

        // Configure fake network response
        fakeNetwork.enqueueResponse(queryUrl, HttpResult.Success(200, "one,two,three,four"))

        val suggestions = searchEngine.getSearchSuggestions(query, maxSuggestions = 2)

        assertEquals(listOf("one", "two"), suggestions)
    }

    @Test
    fun `returns empty list when network call fails`() {
        val query = "kotlin"
        val queryUrl = "https://google.com/complete/search?client=firefox&q=kotlin"

        fakeNetwork.enqueueResponse(queryUrl, HttpResult.Error(500, Throwable("Server error")))

        val suggestions = searchEngine.getSearchSuggestions(query, maxSuggestions = 5)

        assertTrue(suggestions.isEmpty())
    }

    @Test
    fun `respects maxSuggestions limit`() {
        val query = "test"
        val queryUrl = "https://google.com/complete/search?client=firefox&q=test"

        fakeNetwork.enqueueResponse(queryUrl, HttpResult.Success(200, "a,b,c,d,e"))

        val suggestions = searchEngine.getSearchSuggestions(query, maxSuggestions = 3)

        assertEquals(listOf("a", "b", "c"), suggestions)
    }
}

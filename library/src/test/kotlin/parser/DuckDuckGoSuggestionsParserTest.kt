package parser

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class DuckDuckGoSuggestionsParserTest {
    lateinit var duckDuckGoSuggestionsParser: DuckDuckGoSuggestionsParser

    @BeforeEach
    fun setUp() {
        duckDuckGoSuggestionsParser = DuckDuckGoSuggestionsParser()
    }

    @Test
    fun `DDG return size equals maxWords `() {
        val maxWords = 3
        val string =
            "[query,[\" apple \",\" apple music\",\"apple tv\",\"apple id\",\"apple store\",\"applebees\",\"apple watch\",\"applebee's menu\"]]"
        val arr = duckDuckGoSuggestionsParser.parse(string, maxWords)
        assertEquals(arr.size, maxWords)
    }

    @Test
    fun `DDG returns words upto maxWords`() {
        val maxWords = 4
        val string =
            "[query,[\"apple\",\"apple music\",\"apple tv\",\"apple id\",\"apple store\",\"applebees\",\"apple watch\",\"applebee's menu\"]]"
        val arr = duckDuckGoSuggestionsParser.parse(string, maxWords)
        val expectedList = listOf("apple", "apple music", "apple tv","apple id")
        assertEquals(expectedList, arr)
    }
}
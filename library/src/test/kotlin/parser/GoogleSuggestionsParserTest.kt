package parser

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GoogleSuggestionsParserTest {

    private lateinit var parser: GoogleSuggestionsParser

    @BeforeEach
    fun setup() {
        parser = GoogleSuggestionsParser()
    }

    private val sampleJson = """
        ["elon",
          ["elon musk","elon musk net worth","elon musk news","elon musk net worth in rupees",
           "elon musk children","elon musk wife","elon musk father","elongated meaning in hindi",
           "elon musk age","elon musk kaun hai"],
          [],
          {"google:suggestsubtypes":[[512,433],[512,433],[512,433],[512],[512,433],
           [512,433],[512,433],[512],[512,433],[512]]}
        ]
    """.trimIndent()

    @Test
    fun `parse returns empty list for empty json`() {
        val result = parser.parse("", 5)
        assertTrue(result.isEmpty())
    }

    @Test
    fun `parse returns up to maxWords`() {
        val result = parser.parse(sampleJson, 3)
        val expected = listOf("elon musk", "elon musk net worth", "elon musk news")
        assertEquals(expected, result)
    }

    @Test
    fun `parse returns all when maxWords larger than available`() {
        val result = parser.parse(sampleJson, 20)
        assertEquals(10, result.size)  // 10 suggestions available
        assertEquals("elon musk kaun hai", result.last())
    }

    @Test
    fun `parse handles maxWords equal to suggestions count`() {
        val result = parser.parse(sampleJson, 10)
        assertEquals(10, result.size)
    }

    @Test
    fun `parse returns empty list if no suggestions array`() {
        val noSuggestionsJson = """["elon", [], {}]"""
        val result = parser.parse(noSuggestionsJson, 5)
        assertTrue(result.isEmpty())
    }
}

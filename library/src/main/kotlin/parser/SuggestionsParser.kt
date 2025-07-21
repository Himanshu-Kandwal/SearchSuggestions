package parser

interface SuggestionsParser {
    fun parse(response: String, take: Int = Int.MAX_VALUE): List<String>
}
package parser

/**
 * Implementation of [SuggestionsParser] for DuckDuckGo search engine.
 */
class DuckDuckGoSuggestionsParser : SuggestionsParser {
    override fun parse(response: String, take: Int): List<String> {
        val start = response.lastIndexOf('[') + 1 //start index is right after opening bracket [
        val end = response.length - 2 //end index is just before two last closing brackets ]]
        return Util.extractWords(response, start, end, take)
    }
}
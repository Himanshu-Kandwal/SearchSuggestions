package parser

/**
 * Implementation of [SuggestionsParser] for DuckDuckGo search engine.
 */
class DuckDuckGoSuggestionsParser : SuggestionsParser {
    override fun parse(response: String, take: Int): List<String> {
        val start = response.lastIndexOf('[') + 1 //start index is right after opening bracket [
        val end = response.length - 2 - 1 //end index is just before two last closing brackets ]]
        return extractWords(response, start, end, take)
    }

    /**
     * Extracts up to [maxWords] comma-separated words from the given [string],
     * within the substring range defined by [start] and [end] (both inclusive).
     *
     * Quotation marks (`"`) are ignored and not included in the extracted words.
     * Leading and trailing whitespace around words is trimmed.
     *
     * @param string the source text containing comma-separated words
     * @param start the start index (inclusive) of the substring to process
     * @param end the end index (inclusive) of the substring to process
     * @param maxWords the maximum number of words to extract
     * @return a list of extracted words, without quotes or surrounding whitespace
     */

    private fun extractWords(string: String, start: Int, end: Int, maxWords: Int): List<String> {
        val wordsList = mutableListOf<String>()
        val sb = StringBuilder()
        var startIdx = start
        while (startIdx <= end && wordsList.size != maxWords) {
            if (string[startIdx] == ',') { //previous word has ended
                wordsList.add(sb.toString().trim()) //add to world list
                sb.clear()
            } else if (string[startIdx] != '"') { //skip " char
                sb.append(string[startIdx])
            }
            startIdx++
        }

        //Add the last word if there's any remaining and we still want more
        if (sb.isNotEmpty() && wordsList.size < maxWords) {
            wordsList.add(sb.toString().trim())
        }
        return wordsList
    }
}
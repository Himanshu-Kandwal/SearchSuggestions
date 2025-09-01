package parser

/**
 * Implementation of [SuggestionsParser] for Google search engine.
 */
class GoogleSuggestionsParser : SuggestionsParser {
    override fun parse(response: String, take: Int): List<String> {
        return extractWordsFromJson(response, take)
    }

    /**
     * Extracts relevant substring from large JSON string
     */
    private fun extractWordsFromJson(json: String, maxWords: Int): List<String> {
        if (json.isEmpty()) return emptyList()

        var startIdx = -1
        var depth = 0
        val ansList = mutableListOf<String>()
        var wordsTaken = 0

        // find index of nested array that contains suggestions
        for ((idx, ch) in json.withIndex()) {
            if (ch == '[') {
                depth++
                if (depth == 2) {
                    startIdx = idx
                    break
                }
            }
        }

        if (startIdx == -1) return emptyList()

        var currIdx = startIdx + 1 // skipping first [
        val currSb = StringBuilder()
        var insideQuotes = false

        while (currIdx < json.length && wordsTaken < maxWords) {
            val ch = json[currIdx]
            when (ch) {
                '"' -> {
                    insideQuotes = !insideQuotes
                    if (!insideQuotes && currSb.isNotEmpty()) {
                        ansList.add(currSb.toString())
                        currSb.clear()
                        wordsTaken++
                    }
                }

                ']' -> if (!insideQuotes) break
                else -> if (insideQuotes) currSb.append(ch)
            }
            currIdx++
        }

        return ansList
    }
}
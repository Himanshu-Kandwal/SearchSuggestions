package parser

object Util {
    fun extractWords(string: String, start: Int, end: Int, maxWords: Int): List<String> {
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
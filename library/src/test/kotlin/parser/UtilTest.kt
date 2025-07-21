package parser

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class UtilTest {
    @Test
    fun `extractWords return size equals to maxWords`() {
        val maxWords = 4
        val string =
            "\" apple \",\" apple music\",\"apple tv\",\"apple id\",\"apple store\",\"applebees\",\"apple watch\",\"applebee's menu\""
        val arr = Util.extractWords(string, 0, string.length - 1, maxWords)
        assertEquals(arr.size, maxWords)
    }

    @Test
    fun `extractWords returns exactly five results`() {
        val maxWords = 8
        val string = "\"apple\",\"apple music\",\"apple tv\",\"apple id\",\"apple store\",\"applebees\",\"apple watch\",\"applebee's menu\""
        val expectedList = listOf("apple", "apple music", "apple tv","apple id")
        val list = Util.extractWords(string, 0, string.length - 1, maxWords)
        assertEquals(expectedList, list)
    }
}
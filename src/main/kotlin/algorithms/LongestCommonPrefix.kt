package algorithms

import utils.test

/**
 * Write a function to find the longest common prefix string amongst an array of strings.
 *
 * If there is no common prefix, return an empty string "".
 *
 *
 *
 * Example 1:
 *
 * Input: strs = ["flower","flow","flight"]
 * Output: "fl"
 *
 * Example 2:
 *
 * Input: strs = ["dog","racecar","car"]
 * Output: ""
 * Explanation: There is no common prefix among the input strings.
 *
 *
 *
 * Constraints:
 *
 *     1 <= strs.length <= 200
 *     0 <= strs[i].length <= 200
 *     strs[i] consists of only lowercase English letters.
 *
 * https://leetcode.com/problems/longest-common-prefix/
 */
fun main() = test(
    testData = listOf(
        arrayOf("reflower","flow","flight") to "",
        arrayOf("ab", "a") to "a",
        arrayOf("flower","flow","flight") to "fl",
        arrayOf("dog", "racecar", "car") to "",
    ),
    testFunctionExecution = ::longestCommonPrefix
)

fun longestCommonPrefix(strs: Array<String>): String {
    if (strs.size == 1) {
        return strs.first()
    }

    var result = strs.first()

    for (i in 1..strs.lastIndex) {
        val word = strs[i]
        for (j in 0..word.lastIndex) {
            val current = word[j]
            val common = result.getOrNull(j)
            if (current != common) {
                result = word.substring(0 until j)
                break
            }
        }
        if (word.length < result.length) {
            result = word
        }
        if (result.isEmpty()) {
            return ""
        }
    }

    return result
}
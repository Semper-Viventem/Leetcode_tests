package algorithms

import utils.test

/**
 * Given an input string (s) and a pattern (p), implement wildcard pattern matching with support for '?' and '*' where:
 *
 *     '?' Matches any single character.
 *     '*' Matches any sequence of characters (including the empty sequence).
 *
 * The matching should cover the entire input string (not partial).
 *
 *
 *
 * Example 1:
 *
 * Input: s = "aa", p = "a"
 * Output: false
 * Explanation: "a" does not match the entire string "aa".
 *
 * Example 2:
 *
 * Input: s = "aa", p = "*"
 * Output: true
 * Explanation: '*' matches any sequence.
 *
 * Example 3:
 *
 * Input: s = "cb", p = "?a"
 * Output: false
 * Explanation: '?' matches 'c', but the second letter is 'a', which does not match 'b'.
 *
 *
 *
 * Constraints:
 *
 *     0 <= s.length, p.length <= 2000
 *     s contains only lowercase English letters.
 *     p contains only lowercase English letters, '?' or '*'.
 *
 * @see https://leetcode.com/problems/wildcard-matching/description/
 *
 */
fun main() = test(
    testData = listOf(
        ("aa" to "a") to false,
        ("aa" to "*") to true,
        ("cb" to "?a") to false,
        ("" to "*****") to true,
        ("mississippi" to "m??*ss*?i*pi") to false,
    ),
    testFunctionExecution = { (s, p) ->
        isMatch(s, p)
    }
)

private fun isMatch(s: String, p: String): Boolean {
    return isMatch(s, p, state = Array(s.length + 1) { Array(p.length + 1) { null } })
}

private fun isMatch(s: String, p: String, state: Array<Array<Boolean?>>): Boolean {
    if (state[s.length][p.length] != null) {
        return state[s.length][p.length]!!
    }

    val result = when (val mask = p.firstOrNull()) {
        '?' -> {
            val current = s.firstOrNull()
            current != null && isMatch(s.substring(1), p.substring(1), state)
        }
        null -> {
            s.isEmpty()
        }
        '*' -> {
            val pStart = p.indexOfFirst { it != '*' }
            pStart == -1 || (0..s.lastIndex).any { i ->
                isMatch(s.substring(i), p.substring(pStart), state)
            }
        }
        else -> {
            val current = s.firstOrNull()
            current == mask && isMatch(s.substring(1), p.substring(1), state)
        }
    }

    state[s.length][p.length] = result
    return state[s.length][p.length]!!
}
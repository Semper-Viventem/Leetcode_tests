import java.lang.IllegalStateException

/**
 * Given an input string s and a pattern p, implement regular expression matching with support for '.' and '*' where:
 *
 *     '.' Matches any single character.
 *     '*' Matches zero or more of the preceding element.
 *
 * The matching should cover the entire input string (not partial).
 *
 * Example 1:
 *
 * Input: s = "aa", p = "a"
 * Output: false
 * Explanation: "a" does not match the entire string "aa".
 *
 * Example 2:
 *
 * Input: s = "aa", p = "a*"
 * Output: true
 * Explanation: '*' means zero or more of the preceding element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".
 *
 * Example 3:
 *
 * Input: s = "ab", p = ".*"
 * Output: true
 * Explanation: ".*" means "zero or more (*) of any character (.)".
 *
 * https://leetcode.com/problems/regular-expression-matching/
 */
fun main() {
    test(
        testData = listOf(
            ("aa" to "a") to false,
            ("aa" to "a*") to true,
            ("ab" to ".*") to true,
            ("a" to "") to false,
            ("" to ".") to false,
            ("ab" to "a*b") to true,
            ("abcd" to "a*d") to false,
            ("abcd" to "a..d") to true,
            ("abcd" to "a.d") to false,
            ("abcd" to "a*e") to false,
            ("abee" to "a*e") to false,
            ("aab" to "c*a*b") to true,
            ("mississippi" to "mis*is*p*.") to false,
            ("aaa" to "a*a") to true,
            ("mississippi" to "mis*is*ip*.") to true,
            ("aaa" to "ab*a") to false,
            ("bbbba" to ".*a*a") to true,
            ("ab" to ".*..") to true,
            ("abcdede" to "ab.*de") to true
        ),
        testFunctionExecution = { (s, p) -> isMatch(s, p) },
        //runOnlyCaseNr = 7,
    )
}

fun isMatch(s: String, p: String): Boolean {
    return dp(s, p)
//    return recursion(s, p)
//    return stdLib(s, p)
}

fun dp(s: String, p: String): Boolean {
    val dp = Array(s.length + 1) { BooleanArray(p.length + 1) }
    dp[0][0] = true
    for (i in 1..p.length) {
        dp[0][i] = if (p[i - 1] == '*') dp[0][i - 2] else false
    }

    for (i in 1..s.length) {
        for (j in 1..p.length) {
            if (p[j - 1] == s[i - 1] || p[j - 1] == '.') {
                dp[i][j] = dp[i - 1][j - 1]
            } else if (p[j - 1] == '*') {
                dp[i][j] = dp[i][j - 2] ||
                        dp[i - 1][j] && (s[i - 1] == p[j - 2] || p[j - 2] == '.')
            }
        }
    }
    return dp[s.length][p.length]
}

fun recursion(s: String, p: String): Boolean {
    if (p.isEmpty()) return s.isEmpty()

    if (p.length >= 2 && p[1] == '*' && s.length > 0 && (s[0] == p[0] || p[0] == '.')) {
        return isMatch(s.substring(1), p) || isMatch(s, p.substring(2))
    } else if (p.length >= 2 && p[1] == '*') {
        return isMatch(s, p.substring(2))
    }

    return if (s.length > 0) {
        if (s[0] == p[0] || p[0] == '.') {
            isMatch(s.substring(1), p.substring(1))
        } else {
            false
        }
    } else {
        false
    }
}

fun stdLib(s: String, p: String): Boolean {
    return p.toRegex().matches(s)
}
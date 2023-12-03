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
            ("ab" to ".*.") to true,
            ("ab" to ".*..") to true,
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
        ),
        testFunctionExecution = { (s, p) -> isMatch(s, p) },
        runOnlyCaseNr = 7,
    )
}

fun isMatch(s: String, p: String): Boolean {
    if (p.isEmpty()) return s.isEmpty()
    if (s.isEmpty()) return p == "*"

    var i = 0
    var maskIndex = 0

    var maskMode: MaskMode = MaskMode.Initializing

    fun nextMask() {
        val newMask = getMaskMode(p, maskIndex)
        println(newMask)

        val currentPlural = maskMode as? MaskMode.PluralOptional
        val shouldSkip = (newMask is MaskMode.PluralOptional && (!newMask.check(s.getOrNull(i + 1)) && !newMask.check(s.getOrNull(i))))
                || currentPlural != null && newMask !is MaskMode.SingleOptional && newMask.check(currentPlural.symbol)

        if (newMask is MaskMode.PluralOptional) {
            maskIndex++
        }

        if (shouldSkip) {
            maskIndex++
            println("SKIP MASK")
            nextMask()
        } else {
            maskMode = newMask
            maskIndex++
        }
    }

    nextMask()

    while (maskMode !is MaskMode.MaskIsFinished) {

        val current = s.getOrNull(i)
        println("Current: $current")

        when (val mode = maskMode) {
            is MaskMode.StrictMatch -> {
                if (mode.symbol != current) return false
                nextMask()
            }

            is MaskMode.SingleOptional -> {
                if (current == null) return false
                nextMask()
            }

            is MaskMode.PluralOptional -> {
                val next = s.getOrNull(i + 1)
                if (!mode.check(next) || !mode.check(current)) {
                    nextMask()
                }
            }

            is MaskMode.MaskIsFinished -> {
                return current == null
            }

            is MaskMode.Initializing -> {
                throw IllegalStateException("Mode cannot be Initializing at this moment")
            }
        }
        i++
    }

    return i > s.lastIndex
}

private fun getMaskMode(pattern: String, index: Int): MaskMode {
    return when {
        pattern.lastIndex < index -> MaskMode.MaskIsFinished
        pattern.getOrNull(index + 1) == '*' -> MaskMode.PluralOptional(pattern.getOrNull(index))
        pattern[index] == '.' -> MaskMode.SingleOptional

        else -> MaskMode.StrictMatch(pattern[index])
    }
}

private sealed interface MaskMode {

    fun check(char: Char?): Boolean

    data object Initializing : MaskMode {
        override fun check(char: Char?): Boolean {
            throw IllegalStateException("Initializing state doesn't support check")
        }
    }

    data class StrictMatch(val symbol: Char?) : MaskMode {
        override fun check(char: Char?): Boolean {
            return char == symbol
        }
    }

    data object SingleOptional : MaskMode {
        override fun check(char: Char?): Boolean {
            return true
        }
    }

    data class PluralOptional(val symbol: Char?) : MaskMode {
        override fun check(char: Char?): Boolean {
            return char != null && (symbol == '.' || char == symbol)
        }
    }

    data object MaskIsFinished : MaskMode {
        override fun check(char: Char?): Boolean {
            return false
        }
    }
}
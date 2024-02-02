package algorithms

import utils.test

/**
 * Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent. Return the answer in any order.
 *
 * A mapping of digits to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.
 *
 * Example 1:
 *
 * Input: digits = "23"
 * Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
 *
 * Example 2:
 *
 * Input: digits = ""
 * Output: []
 *
 * Example 3:
 *
 * Input: digits = "2"
 * Output: ["a","b","c"]
 *
 * https://leetcode.com/problems/letter-combinations-of-a-phone-number/
 *
 */
fun main() = test(
    testData = listOf(
        "23" to listOf("ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"),
        "" to listOf(),
        "2" to listOf("a", "b", "c"),
    ),
    testFunctionExecution = ::letterCombinations
)

val numberPad = hashMapOf(
    '2' to charArrayOf('a', 'b', 'c'),
    '3' to charArrayOf('d', 'e', 'f'),
    '4' to charArrayOf('g', 'h', 'i'),
    '5' to charArrayOf('j', 'k', 'l'),
    '6' to charArrayOf('m', 'n', 'o'),
    '7' to charArrayOf('p', 'q', 'r', 's'),
    '8' to charArrayOf('t', 'u', 'v'),
    '9' to charArrayOf('w', 'x', 'y', 'z'),
)
val builder = StringBuilder()
fun letterCombinations(digits: String): List<String> {
    if (digits.isEmpty()) return emptyList()
    if (digits.length == 1) return numberPad[digits[0]]!!.map { it.toString() }

    return numberPad[digits[0]]!!.flatMap { char ->
        letterCombinations(digits.substring(1)).map { postfix ->
            builder.append(char)
            builder.append(postfix)
            builder.toString().apply { builder.clear() }
        }
    }
}
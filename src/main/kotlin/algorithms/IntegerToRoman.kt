package algorithms

import utils.test
import java.util.*

/**
 * Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
 *
 * Symbol       Value
 * I ----------- 1
 * V ----------- 5
 * X ----------- 10
 * L ----------- 50
 * C ----------- 100
 * D ----------- 500
 * M ----------- 1000
 *
 * For example, 2 is written as II in Roman numeral, just two one's added together. 12 is written as XII, which is simply X + II. The number 27 is written as XXVII, which is XX + V + II.
 *
 * Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not IIII. Instead, the number four is written as IV. Because the one is before the five we subtract it making four. The same principle applies to the number nine, which is written as IX. There are six instances where subtraction is used:
 *
 *     I can be placed before V (5) and X (10) to make 4 and 9.
 *     X can be placed before L (50) and C (100) to make 40 and 90.
 *     C can be placed before D (500) and M (1000) to make 400 and 900.
 *
 * Given an integer, convert it to a roman numeral.
 *
 * Example 1:
 *
 * Input: num = 3
 * Output: "III"
 * Explanation: 3 is represented as 3 ones.
 *
 * Example 2:
 *
 * Input: num = 58
 * Output: "LVIII"
 * Explanation: L = 50, V = 5, III = 3.
 *
 * Example 3:
 *
 * Input: num = 1994
 * Output: "MCMXCIV"
 * Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
 *
 * https://leetcode.com/problems/integer-to-roman/
 */
fun main() = test(
    testData = listOf(
        900 to "CM",
        400 to "CD",
        90 to "XC",
        40 to "XL",
        1 to "I",
        3 to "III",
        4 to "IV",
        58 to "LVIII",
        1994 to "MCMXCIV",
        1000 to "M",
    ),
    testFunctionExecution = ::intToRoman,
)

fun intToRoman(num: Int): String {
    val values = LinkedList(
        listOf(
            1000 to "M",
            900 to "CM",
            500 to "D",
            400 to "CD",
            100 to "C",
            90 to "XC",
            50 to "L",
            40 to "XL",
            10 to "X",
            9 to "IX",
            5 to "V",
            4 to "IV",
            1 to "I",
        )
    )
    val result = StringBuffer()
    var left = num

    while (values.isNotEmpty()) {
        val (digit, sign) = values[0]

        val count = left / digit
        if (count > 0) {
            (0..<count).map { result.append(sign) }
            left %= count * digit
        }
        values.removeAt(0)
    }

    return result.toString()
}

fun intToRoman1(num: Int): String {
    val result = StringBuffer()
    var left = num

    while (left > 0) {
        when {
            left / 1000 > 0 -> {
                val count = left / 1000
                result.append(CharArray(count) { 'M' })
                left %= count * 1000
            }

            left / 900 > 0 -> {
                result.append("CM")
                left -= 900
            }

            left / 500 > 0 -> {
                val count = left / 500
                result.append(CharArray(count) { 'D' })
                left %= count * 500
            }

            left / 400 > 0 -> {
                result.append("CD")
                left -= 400
            }

            left / 100 > 0 -> {
                val count = left / 100
                result.append(CharArray(count) { 'C' })
                left %= count * 100
            }

            left / 90 > 0 -> {
                result.append("XC")
                left -= 90
            }

            left / 50 > 0 -> {
                val count = left / 50
                result.append(CharArray(count) { 'L' })
                left %= count * 50
            }

            left / 40 > 0 -> {
                result.append("XL")
                left -= 40
            }

            left / 10 > 0 -> {
                val count = left / 10
                result.append(CharArray(count) { 'X' })
                left %= count * 10
            }

            left / 9 > 0 -> {
                result.append("IX")
                left -= 9
            }

            left / 5 > 0 -> {
                val count = left / 5
                result.append(CharArray(count) { 'V' })
                left %= count * 5
            }

            left / 4 > 0 -> {
                result.append("IV")
                left -= 4
            }

            else -> {
                result.append(CharArray(left) { 'I' })
                left = 0
            }
        }
    }

    return result.toString()
}
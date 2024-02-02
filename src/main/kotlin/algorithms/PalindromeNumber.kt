package algorithms

import utils.test
import kotlin.math.abs
import kotlin.math.pow

/**
 * https://leetcode.com/problems/palindrome-number/description/
 */
fun main() {
    test(
        testData = listOf(
            121 to true,
            -121 to false,
            10 to false,
            1 to true,
            32123 to true,
            1231 to false,
            0 to true,
            1000021 to false,
            1001 to true,
            1001001 to true,
        ),
        testFunctionExecution = ::isPalindrome,
    )
}

fun isPalindrome(x: Int): Boolean {
    if (x < 0) return false
    if (x / 10 == 0) return true

    fun reversed(input: Int): Int {
        var origin = input
        var output = 0
        var finished = false
        val max = Int.MAX_VALUE / 10
        while (!finished) {
            if (max < abs(output)) {
                return 0
            }

            val digit = origin % 10

            finished = abs(origin / 10) <= 0
            origin /= 10

            output = output * 10 + digit
        }
        return output
    }

    return x == reversed(x)
}

fun isPalindrome2(x: Int): Boolean {
    if (x < 0) return false
    if (x / 10 == 0) return true

    val str = x.toString()

    var l = 0
    var r = str.lastIndex

    while (l <= str.length / 2) {
        if (str[l] != str[r]) return false
        l++
        r--
    }

    return true
}

fun isPalindrome1(x: Int): Boolean {
    if (x < 0) return false
    if (x == 0) return true

    fun lenght(x: Int): Int {
        var number = x
        var lenght = 0

        while (number > 0) {
            lenght++
            number /= 10
        }
        return lenght
    }

    val length = lenght(x)

    if (length == 1) return true

    val r = x % 10
    val l = x / Math.pow(10.0, ((length - 1.0))).toInt()

    val isTheSame = r == l
    if (length == 2 || length == 3) return isTheSame

    val next = (x / 10) % 10.0.pow((length - 2.0)).toInt()
    if (length - lenght(next) > 2) return false
    return isTheSame && isPalindrome1(next)
}
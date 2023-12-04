/**
 *You are given a string num representing a large integer. An integer is good if it meets the following conditions:
 *
 *     It is a substring of num with length 3.
 *     It consists of only one unique digit.
 *
 * Return the maximum good integer as a string or an empty string "" if no such integer exists.
 *
 * Note:
 *
 *     A substring is a contiguous sequence of characters within a string.
 *     There may be leading zeroes in num or a good integer.
 *
 *
 *
 * Example 1:
 *
 * Input: num = "6777133339"
 * Output: "777"
 * Explanation: There are two distinct good integers: "777" and "333".
 * "777" is the largest, so we return "777".
 *
 * Example 2:
 *
 * Input: num = "2300019"
 * Output: "000"
 * Explanation: "000" is the only good integer.
 *
 * Example 3:
 *
 * Input: num = "42352338"
 * Output: ""
 * Explanation: No substring of length 3 consists of only one unique digit. Therefore, there are no good integers.
 *
 *
 * https://leetcode.com/problems/largest-3-same-digit-number-in-string/?envType=daily-question&envId=2023-12-04
 */
fun main() = test(
    testData = listOf(
        "6777133339" to "777",
        "2300019" to "000",
        "000999" to "999",
        "42352338" to "",
    ),
    testFunctionExecution = ::largestGoodInteger
)

fun largestGoodInteger(num: String): String {
    var max = Char.MIN_VALUE

    for (r in 2..num.lastIndex) {
        if (num[r] == num[r - 1] && num[r - 1] == num[r - 2] && num[r] > max) {
            if (num[r] == '9') return "999"
            max = num[r]
        }
    }

    return if (max != Char.MIN_VALUE) "$max$max$max" else ""
}
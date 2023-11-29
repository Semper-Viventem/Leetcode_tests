import kotlin.math.abs

/**
 * https://leetcode.com/problems/reverse-integer/
 */
fun main() {
    test(
        testData = listOf(
            123 to 321,
            -123 to -321,
            120 to 21,
            1534236469 to 0,
            214748364 to 463847412,
            2147483647 to 0,
            746384741 to 147483647,
            -2147483412 to -2143847412,
        ),
        testFunctionExecution = ::reversedInteger,
    )
}

private fun reversedInteger(input: Int): Int {
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
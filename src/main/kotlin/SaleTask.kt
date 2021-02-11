/**
 * There is an array of numbers - this is the price of some product for N days. Every day you produce a new unit of product.
 * You can sell the product at today's price, or save it the next day and sell at a higher price. It is necessary to implement a function that will calculate the greatest profit with the most optimal strategy.
 * For example:
 *
 * 1, 3, 1, 2 -> 10
 * 3, 1, 1, 2, 1 -> 9
 */
fun main() {
    test(
        testData = listOf(
            intArrayOf(1, 1, 1, 2) to 8,
            intArrayOf(2, 1, 1, 1) to 5,
            intArrayOf(1, 3, 1, 2) to 10,
            intArrayOf(3, 1, 1, 2, 1) to 10,
            intArrayOf(2, 2, 1, 1, 3) to 15,
            intArrayOf(1) to 1,
            intArrayOf(1, 1, 1, 3, 1) to 13
        ),
        testFunctionExecution = ::process
    )
}

/**
 * Runtime complexity: O(n)
 * Size complexity: O(1)
 */
fun process(data: IntArray): Int {
    var result = 0
    var currentMaxValue = data.last()

    for (i in data.lastIndex downTo 0) {
        val value = data[i]
        if (value > currentMaxValue) {
            currentMaxValue = value
        }
        result += currentMaxValue
    }

    return result
}
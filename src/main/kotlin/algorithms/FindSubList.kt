package algorithms

import utils.test

/**
 * There is array of random ints. You have to find sublist with numbers where each number less or equals
 * of sum of another two any numbers in this sublist. And return sum of this sublist.
 *
 * Example:
 * 1, 7, 3, 15, 2, 5, 2, 1, 4 -> 22 (7, 15)
 * 3, 3, 3, 1, 3, 7, 1 -> 13 (3, 3, 3, 1, 3)
 *
 * Good sublist: [1, 2, 1]: 1 + 1 !< 2
 * Bad sublist: [1, 7, 15]: 1 + 7 < 15
 */
fun main() {
    test(
        testData = listOf(
            intArrayOf(1) to 1,
            intArrayOf(1, 1) to 2,
            intArrayOf(0, 0, 0) to 0,
            intArrayOf(1, 0, 0) to 1,
            intArrayOf(0, 0, 1) to 1,
            intArrayOf(3, 5, 4, 7, 1) to 19,
            intArrayOf(1, 7, 3, 15, 2, 5, 2, 1, 4) to 22,
            intArrayOf(3, 3, 3, 1, 3, 7, 1) to 13,
            intArrayOf(1, 2, 1) to 4
        ),
        testFunctionExecution = ::process
    )
}

/**
 * I found solution only with pre-processed sorted data but it need to n log(n) complexity for sorting
 * Runtime complexity: O(n^2)
 * Memory complexity: O(1)
 */
private fun process(data: IntArray): Int {
    if (data.size < 3) return data.sum()

    var totalMax = 0

    // O(n log(n))
    data.sort()

    for (i in (0 until data.lastIndex)) {

        val minItem1 = data[i]
        val minItem2 = data[i + 1]
        val sum = minItem1 + minItem2

        var maxInRange = sum

        for (j in (i + 2)..(data.lastIndex)) {
            val currentMaxValue = data[j]
            if (currentMaxValue > sum) {
                break
            } else {
                maxInRange += currentMaxValue
            }
        }
        if (maxInRange > totalMax) totalMax = maxInRange
    }

    return totalMax
}
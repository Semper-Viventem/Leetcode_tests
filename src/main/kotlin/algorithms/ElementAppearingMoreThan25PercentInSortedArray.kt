package algorithms

import utils.test

/**
 * Given an integer array sorted in non-decreasing order, there is exactly one integer in the array that occurs more than 25% of the time, return that integer.
 *
 *
 *
 * Example 1:
 *
 * Input: arr = [1,2,2,6,6,6,6,7,10]
 * Output: 6
 *
 * Example 2:
 *
 * Input: arr = [1,1]
 * Output: 1
 *
 * https://leetcode.com/problems/element-appearing-more-than-25-in-sorted-array/?envType=daily-question&envId=2023-12-11
 *
 */
fun main() = test(
    testData = listOf(
        intArrayOf(1, 2, 2, 6, 6, 6, 6, 7, 10) to 6,
        intArrayOf(1, 1) to 1,
        intArrayOf(1, 2, 3, 3) to 3,
    ),
    testFunctionExecution = ::findSpecialInteger
)

fun findSpecialInteger(arr: IntArray): Int {
    if (arr.size <= 2) return arr[0]
    var i = 1
    var appearingCount = 1
    val targetAppearingCount = arr.size * 0.25

    while (i <= arr.lastIndex) {
        if (arr[i - 1] != arr[i]) {
            appearingCount = 1
        } else {
            appearingCount++
        }

        if (appearingCount > targetAppearingCount) {
            return arr[i]
        }

        i++
    }

    return 0
}
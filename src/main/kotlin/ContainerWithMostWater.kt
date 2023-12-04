import kotlin.math.max
import kotlin.math.min

/**
 *
 * You are given an integer array height of length n. There are n vertical lines drawn such that the two endpoints of the ith line are (i, 0) and (i, height[i]).
 *
 * Find two lines that together with the x-axis form a container, such that the container contains the most water.
 *
 * Return the maximum amount of water a container can store.
 *
 * Notice that you may not slant the container.
 *
 * Example 1:
 *
 * Input: height = [1,8,6,2,5,4,8,3,7]
 * Output: 49
 * Explanation: The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7]. In this case, the max area of water (blue section) the container can contain is 49.
 *
 * Example 2:
 *
 * Input: height = [1,1]
 * Output: 1
 *
 * https://leetcode.com/problems/container-with-most-water/
 */
fun main() = test(
    testData = listOf(
        intArrayOf(1,8,6,2,5,4,8,3,7) to 49,
        intArrayOf(1, 1) to 1,
    ),
    testFunctionExecution = ::maxArea
)

/**
 * Time complexity: O(n)
 *
 * Memory complexity: O(1)
 */
fun maxArea(height: IntArray): Int {
    var bestResult: Int = 0
    var lIndex = 0
    var rIndex = height.lastIndex

    while (lIndex != rIndex) {
        val l = height[lIndex]
        val r = height[rIndex]

        val y = min(l, r)
        val x = rIndex - lIndex

        val volume = x * y

        if (l > r) {
            rIndex--
        } else {
            lIndex++
        }

        bestResult = max(bestResult, volume)
    }

    return bestResult
}

/**
 * Time complexity: O(n^2)
 *
 * Memory complexity: O(1)
 */
fun maxArea1(height: IntArray): Int {
    var bestResult: Int = 0

    for (i in 0 until height.lastIndex) {
        for (j in (i + 1)..height.lastIndex) {
            val l = height[i]
            val r = height[j]

            val y = min(l, r)
            val x = j - i

            bestResult = max(bestResult, x * y)
        }
    }

    return bestResult
}
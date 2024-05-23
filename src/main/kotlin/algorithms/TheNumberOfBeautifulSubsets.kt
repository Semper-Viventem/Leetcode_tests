package algorithms

import utils.test

/**
 * @see https://leetcode.com/problems/the-number-of-beautiful-subsets/description/?envType=daily-question&envId=2024-05-23
 *
 * You are given an array nums of positive integers and a positive integer k.
 *
 * A subset of nums is beautiful if it does not contain two integers with an absolute difference equal to k.
 *
 * Return the number of non-empty beautiful subsets of the array nums.
 *
 * A subset of nums is an array that can be obtained by deleting some (possibly none) elements from nums. Two subsets are different if and only if the chosen indices to delete are different.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [2,4,6], k = 2
 * Output: 4
 * Explanation: The beautiful subsets of the array nums are: [2], [4], [6], [2, 6].
 * It can be proved that there are only 4 beautiful subsets in the array [2,4,6].
 *
 * Example 2:
 *
 * Input: nums = [1], k = 1
 * Output: 1
 * Explanation: The beautiful subset of the array nums is [1].
 * It can be proved that there is only 1 beautiful subset in the array [1].
 *
 */
fun main() = test(
    testData = listOf(
        (intArrayOf(2, 4, 6) to 2) to 4,
        (intArrayOf(1) to 1) to 1,
        (intArrayOf(19, 14, 10, 12, 17, 8, 20, 18, 7, 13, 9, 6, 1, 11, 5) to 19) to 24575,
        (intArrayOf(10, 4, 5, 7, 2, 1) to 3) to 23,
        (intArrayOf(10, 2, 6, 4, 5, 7, 3, 9, 1, 8) to 3) to 199,
        (intArrayOf(6, 7, 3, 9) to 3) to 9,
        (intArrayOf(1, 2, 3, 3) to 1) to 8,
        (intArrayOf(14, 10, 24, 25, 29, 8, 27, 26, 15, 11, 3, 19, 23, 5, 22, 16, 28, 17) to 27) to 262143,
        (intArrayOf(51, 15, 61, 64, 53, 6, 3, 5, 76, 79, 67, 26, 87, 76, 54, 50, 42, 80, 79) to 74) to 245759,
    ),
    testFunctionExecution = { (nums, k) -> beautifulSubsets(nums, k) },
    inputToString = { (nums, k) -> (nums.sorted() to k).toString() },
    //runOnlyCaseNr = 5,
)

fun beautifulSubsets(nums: IntArray, k: Int): Int {
    if (nums.size == 1 || nums.isEmpty()) return nums.size
    nums.sort()
    return backtrack(nums, k, 0b0, 0b0, 0, hashMapOf())
}

fun backtrack(nums: IntArray, k: Int, mask: Int, prohibited: Int, l: Int, dp: MutableMap<Int, Int>): Int {

    if (dp[mask] != null) {
        return dp[mask]!!
    }

    var result = 0

    for (i in l..nums.lastIndex) {

        val itemMask = 0b1 shl i
        val item = nums[i]

        if ((mask or prohibited) and itemMask == 0) {

            val prohibitedMask = getMaskOfElementWithId(nums, item + k) or getMaskOfElementWithId(nums, item - k)

            val tails = backtrack(nums, k, mask or itemMask, prohibited or prohibitedMask, i + 1, dp)

            result += tails + 1
        }
    }

    dp[mask] = result
    return dp[mask]!!
}

private fun getMaskOfElementWithId(nums: IntArray, target: Int): Int {
    var l = nums.binarySearch(target)

    if (l < 0) return 0

    while (l - 1 >= 0 && nums[l - 1] == target) {
        l--
    }

    var r = l

    while (r + 1 <= nums.lastIndex && nums[r + 1] == target) {
        r++
    }

    var result = 0
    for (i in l..r) {
        result = result or (0b1 shl i)
    }

    return result
}

private fun getMaskOfElementWithIdLinear(nums: IntArray, target: Int): Int {
    var result = 0

    for (i in nums.indices) {
        if (nums[i] == target) {
            result = result or (0b1 shl i)
        }
    }

    return result
}
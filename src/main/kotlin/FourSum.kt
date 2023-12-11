/**
 * Given an array nums of n integers, return an array of all the unique quadruplets [nums[a], nums[b], nums[c], nums[d]] such that:
 *
 *     0 <= a, b, c, d < n
 *     a, b, c, and d are distinct.
 *     nums[a] + nums[b] + nums[c] + nums[d] == target
 *
 * You may return the answer in any order.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,0,-1,0,-2,2], target = 0
 * Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
 *
 * Example 2:
 *
 * Input: nums = [2,2,2,2,2], target = 8
 * Output: [[2,2,2,2]]
 *
 */
fun main() = test(
    testData = listOf(
        (intArrayOf(1, 0, -1, 0, -2, 2) to 0) to listOf(listOf(-2, -1, 1, 2), listOf(-2, 0, 0, 2), listOf(-1, 0, 0, 1)).ordered(),
        (intArrayOf(2, 2, 2, 2, 2) to 8) to listOf(listOf(2, 2, 2, 2)).ordered(),
        (intArrayOf(1000000000,1000000000,1000000000,1000000000) to -294967296) to emptyList()
    ),
    testFunctionExecution = { (nums, target) -> fourSum(nums, target) },
    inputToString = { (it.first.toList() to it.second).toString() },
)
private fun List<List<Int>>.ordered(): List<List<Int>> = this.map { it.sorted() }.sortedBy { it.toString() }

fun fourSum(nums: IntArray, target: Int): List<List<Int>> {
    return nSum(nums, 1L* target, 4).ordered()
}
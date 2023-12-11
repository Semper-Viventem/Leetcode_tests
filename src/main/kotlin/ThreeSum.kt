/**
 * Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
 *
 * Notice that the solution set must not contain duplicate triplets.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [-1,0,1,2,-1,-4]
 * Output: [[-1,-1,2],[-1,0,1]]
 * Explanation:
 * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
 * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
 * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
 * The distinct triplets are [-1,0,1] and [-1,-1,2].
 * Notice that the order of the output and the order of the triplets does not matter.
 *
 * Example 2:
 *
 * Input: nums = [0,1,1]
 * Output: []
 * Explanation: The only possible triplet does not sum up to 0.
 *
 * Example 3:
 *
 * Input: nums = [0,0,0]
 * Output: [[0,0,0]]
 * Explanation: The only possible triplet sums up to 0.
 *
 * https://leetcode.com/problems/3sum/
 */
fun main() = test(
    testData = listOf(
        intArrayOf(-1, 0, 1, 2, -1, -4) to listOf(listOf(-1, 0, 1), listOf(-1, -1, 2)).ordered(),
        intArrayOf(0, 1, 1) to listOf<List<Int>>().ordered(),
        intArrayOf(0, 0, 0) to listOf(listOf(0, 0, 0)).ordered(),
        intArrayOf(0, 0, 0, 0) to listOf(listOf(0, 0, 0)).ordered(),
    ),
    testFunctionExecution = { threeSum(it).ordered() },
)

private fun List<List<Int>>.ordered(): List<List<Int>> = this.map { it.sorted() }.sortedBy { it.toString() }

fun threeSum(nums: IntArray): List<List<Int>> {
    nums.sort()

    val result = mutableListOf<List<Int>>()
    for (i in 0..nums.lastIndex) {
        val current = nums[i]
        val expectedSum = 0 - current
        if (i == 0 || current != nums[i - 1]) {
            twoSum(nums, i + 1, expectedSum, current, result)
        }
    }

    return result.toList()
}

fun twoSum(nums: IntArray, leftIndex: Int, targetSum: Int, current: Int, result: MutableList<List<Int>>) {
    var l = leftIndex
    var r = nums.lastIndex
    while (l < r) {
        val left = nums[l]
        if (left + nums[r] == targetSum) {
            result.add(listOf(current, nums[l], nums[r]))
        }

        if (left <= targetSum - nums[r]) {
            while (nums.getOrNull(l) == left) {
                l++
            }
        } else {
            r--
        }
    }
}
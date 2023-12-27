/**
 * Given an integer array nums and an integer k, return true if it is possible to divide this array into k non-empty subsets whose sums are all equal.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [4,3,2,3,5,2,1], k = 4
 * Output: true
 * Explanation: It is possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
 *
 * Example 2:
 *
 * Input: nums = [1,2,3,4], k = 3
 * Output: false
 *
 */
fun main() = test(
    testData = listOf(
        (intArrayOf(1) to 1) to true,
        (intArrayOf(1) to 2) to false,
        (intArrayOf(4, 4) to 2) to true,
        (intArrayOf(4, 3, 2, 3, 5, 2, 1) to 4) to true,
        (intArrayOf(1, 2, 3, 4) to 3) to false,
        (intArrayOf(4, 4, 6, 2, 3, 8, 10, 2, 10, 7) to 4) to true,
        (intArrayOf(2, 2, 2, 2, 3, 4, 5) to 4) to false,
        (intArrayOf(3522, 181, 521, 515, 304, 123, 2512, 312, 922, 407, 146, 1932, 4037, 2646, 3871, 269) to 5) to true,
    ),
    testFunctionExecution = { (nums, k) -> canPartitionKSubsets(nums, k) },
    inputToString = { (nums, k) -> (nums.toList() to k).toString() },
    clearEnv = {
        checkSubsetDPCache.clear()
    },
)

fun canPartitionKSubsets(nums: IntArray, k: Int): Boolean {
    if (k > nums.size) return false
    if (nums.sum() % k != 0) return false
    val targetSum = nums.sum() / k
    return checkSubsets(i = 0, nums = nums, k = k, reservedIndices = 0b0, targetSum = targetSum, subsetSum = 0)
}

var checkSubsetDPCache = hashMapOf<Int, Boolean>()
fun checkSubsets(
    i: Int,
    nums: IntArray,
    k: Int,
    reservedIndices: Int,
    targetSum: Int,
    subsetSum: Int,
): Boolean {
    if (k == 0) return true
    return checkSubsetDPCache.getOrPut(reservedIndices) {
        var result = false
        if (subsetSum == targetSum) {
            result = checkSubsets(
                0,
                k = k - 1,
                subsetSum = 0,
                targetSum = targetSum,
                reservedIndices = reservedIndices,
                nums = nums
            )
        } else {
            for (j in i until nums.size) {
                val jMask = 0b1 shl j
                if (reservedIndices or jMask != reservedIndices && subsetSum + nums[j] <= targetSum) {
                    val leftGroupsAreCorrect = checkSubsets(
                        i = j + 1,
                        k = k,
                        subsetSum = subsetSum + nums[j],
                        reservedIndices = reservedIndices or jMask,
                        nums = nums,
                        targetSum = targetSum,
                    )

                    if (leftGroupsAreCorrect) {
                        result = true
                        break
                    }
                }

            }
        }
        result
    }
}
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
        (intArrayOf(4,4,6,2,3,8,10,2,10,7) to 4) to true,
        (intArrayOf(2,2,2,2,3,4,5) to 4) to false,
        (intArrayOf(3522,181,521,515,304,123,2512,312,922,407,146,1932,4037,2646,3871,269) to 5) to true,
    ),
    testFunctionExecution = { (nums, k) -> canPartitionKSubsets(nums, k) },
    inputToString = { (nums, k) -> (nums.toList() to k).toString() },
    clearEnv = {
        checkSubsetDPCache.clear()
        groupIndicesDPCache.clear()
    },
    runOnlyCaseNr = 6
)

fun canPartitionKSubsets(nums: IntArray, k: Int): Boolean {
    if (k > nums.size) return false
    return checkSubsets(nums, k, 0b0)
}

var checkSubsetDPCache = hashMapOf<Int, Boolean>()

fun checkSubsets(nums: IntArray, k: Int, reservedIndices: Int, numberOfReservedIndexes: Int = 0, prevSum: Int? = null): Boolean {
    val key = reservedIndices * 100 + k

    if (checkSubsetDPCache[key] != null) {
        return checkSubsetDPCache[key]!!
    }

    if (prevSum != null && sumByInvertedMask(nums, numberOfReservedIndexes) < prevSum) {
        return false
    }

    var groupSize: Int = nums.size - numberOfReservedIndexes
    while (groupSize >= 1) {
        getGroupIndices(nums, groupSize, reservedIndices).forEach { groupIndeces ->
            val isLastGroup = groupSize + numberOfReservedIndexes == nums.size
            val groupSum = sumByMask(nums, groupIndeces)
            val targetSum = prevSum ?: groupSum
            if (groupSum == targetSum
                && ((isLastGroup && k == 1) || (k > 1 && checkSubsets(nums, k - 1, reservedIndices or groupIndeces, numberOfReservedIndexes + groupSize, targetSum)))) {
                checkSubsetDPCache[key] = true
                return true
            }
        }
        groupSize--
    }
    checkSubsetDPCache[key] = false
    return false
}

var groupIndicesDPCache = hashMapOf<Int, Set<Int>>()
fun getGroupIndices(nums: IntArray, groupSize: Int, reservedIndices: Int): Set<Int> {
    if (groupSize == 0) return setOf(0b0)
    val key = groupSize * 100 + reservedIndices

    if (groupIndicesDPCache[key] != null) {
        return groupIndicesDPCache[key]!!
    }

    val result = mutableSetOf<Int>()

    for (i in nums.indices) {
        val indexMask = intToBitMask(i)
        if (reservedIndices or indexMask != reservedIndices) {
            result += getGroupIndices(nums, groupSize - 1, reservedIndices or indexMask).map { indexMask or it }
        }
    }

    groupIndicesDPCache[key] = result
    return result
}

fun sumByMask(nums: IntArray, indexesMask: Int): Int {
    var sum = 0

    for (i in nums.indices) {
        val indexMask = intToBitMask(i)
        if (indexMask or indexesMask == indexesMask) sum += nums[i]
    }

    return sum
}

fun sumByInvertedMask(nums: IntArray, indexesMask: Int): Int {
    var sum = 0

    for (i in nums.indices) {
        val indexMask = intToBitMask(i)
        if (indexMask or indexesMask != indexesMask) sum += nums[i]
    }

    return sum
}

fun intToBitMask(int: Int): Int {
    return 0b1 shl (int)
}
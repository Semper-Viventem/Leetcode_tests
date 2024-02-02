package algorithms

/**
 * A common solution for problems like "Find all unique combinations of K elements which give a target sum"
 *
 * @see [ThreeSum.kt]
 */
fun kSum(
    nums: IntArray,
    targetSum: Long,
    k: Int,
    startPosition: Int = 0,
    isSorted: Boolean = false
): List<List<Int>> {
    if (!isSorted) {
        nums.sort()
    }

    return if (k == 2) { // solution for 2 Sum
        var l = startPosition
        var r = nums.size - 1
        val result = mutableListOf<MutableList<Int>>()

        while (l < r) {
            val sum: Long = nums[l].toLong() + nums[r].toLong()
            if (sum == targetSum) {
                result.add(mutableListOf(nums[l], nums[r]))
                val rNumber = nums[r]
                val lNumber = nums[l]
                while (r > 0 && rNumber == nums[r]) r--
                while (l < nums.size && lNumber == nums[l]) l++
            } else if (sum < targetSum) {
                l++
            } else {
                r--
            }
        }
        result
    } else {
        val result = mutableListOf<List<Int>>()
        for (i in startPosition..nums.size - k) {
            if (i == startPosition || nums[i] != nums[i - 1]) {
                val localResult = algorithms.kSum(nums, targetSum - nums[i], k - 1, i + 1)
                localResult.forEach {
                    result.add(it + nums[i])
                }
            }
        }
        result
    }
}

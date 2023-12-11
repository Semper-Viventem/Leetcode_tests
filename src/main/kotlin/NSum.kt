/**
 * A common solution for problems like "Find all unique combinations of N elements which gives some sum"
 *
 * @see [ThreeSum.kt]
 */
fun nSum(
    nums: IntArray,
    targetSum: Long,
    targetAmount: Int,
    startPosition: Int = 0,
    isSorted: Boolean = false
): List<List<Int>> {
    if (!isSorted) {
        nums.sort()
    }

    return if (targetAmount == 2) { // solution for 2 Sum
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
        for (i in startPosition..nums.size - targetAmount) {
            if (i == startPosition || nums[i] != nums[i - 1]) {
                val localResult = nSum(nums, targetSum - nums[i], targetAmount - 1, i + 1)
                localResult.forEach {
                    result.add(it + nums[i])
                }
            }
        }
        result
    }
}

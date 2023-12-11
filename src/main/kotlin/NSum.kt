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

    val result = mutableSetOf<List<Int>>()

    if (targetAmount == 2) { // solution for 2Sum
        var l = startPosition
        var r = nums.lastIndex
        while (l < r) {
            val left = nums[l].toLong()
            if (left + nums[r] == targetSum) {
                result.add(listOf(nums[l], nums[r]))
            }

            if (left <= targetSum - nums[r]) {
                l++
            } else {
                r--
            }
        }
    } else {
        for (i in startPosition..(nums.lastIndex - (targetAmount - 1))) {
            val current = nums[i]
            val expectedSum = targetSum - current
            val localResult = nSum(
                nums = nums,
                targetSum = expectedSum,
                targetAmount = targetAmount - 1,
                startPosition = i + 1,
                isSorted = true
            )
            result.addAll(localResult.map {
                (listOf(current) + it)
            })
        }
    }

    return result.toList()
}

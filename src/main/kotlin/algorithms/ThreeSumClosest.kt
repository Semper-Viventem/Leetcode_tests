package algorithms

import utils.test
import kotlin.math.abs

/**
 * Given an integer array nums of length n and an integer target, find three integers in nums such that the sum is closest to target.
 *
 * Return the sum of the three integers.
 *
 * You may assume that each input would have exactly one solution.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [-1,2,1,-4], target = 1
 * Output: 2
 * Explanation: The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
 *
 * Example 2:
 *
 * Input: nums = [0,0,0], target = 1
 * Output: 0
 * Explanation: The sum that is closest to the target is 0. (0 + 0 + 0 = 0).
 *
 * https://leetcode.com/problems/3sum-closest/
 */
fun main() = test(
    testData = listOf(
        (intArrayOf(-1, 2, 1, -4) to 1) to 2,
        (intArrayOf(0, 0, 0) to 1) to 0,
        (intArrayOf(-1, 1, 1, -1, 2) to 0) to 0,
        (intArrayOf(
            13,252,-87,-431,-148,387,-290,572,-311,-721,222,673,538,919,483,-128,-518,7,-36,-840,233,-184,-541,522,-162,127,-935,-397,761,903,-217,543,906,-503,-826,-342,599,-726,960,-235,436,-91,-511,-793,-658,-143,-524,-609,-728,-734,273,-19,-10,630,-294,-453,149,-581,-405,984,154,-968,623,-631,384,-825,308,779,-7,617,221,394,151,-282,472,332,-5,-509,611,-116,113,672,-497,-182,307,-592,925,766,-62,237,-8,789,318,-314,-792,-632,-781,375,939,-304,-149,544,-742,663,484,802,616,501,-269,-458,-763,-950,-390,-816,683,-219,381,478,-129,602,-931,128,502,508,-565,-243,-695,-943,-987,-692,346,-13,-225,-740,-441,-112,658,855,-531,542,839,795,-664,404,-844,-164,-709,167,953,-941,-848,211,-75,792,-208,569,-647,-714,-76,-603,-852,-665,-897,-627,123,-177,-35,-519,-241,-711,-74,420,-2,-101,715,708,256,-307,466,-602,-636,990,857,70,590,-4,610,-151,196,-981,385,-689,-617,827,360,-959,-289,620,933,-522,597,-667,-882,524,181,-854,275,-600,453,-942,134
        ) to -2805) to -2805,
        (intArrayOf(-981, -882, -942) to -2805) to -2805,
        (intArrayOf(2,3,8,9,10) to 16) to 15,
    ),
    testFunctionExecution = { (nums, target) -> threeSumClosest(nums, target) },
    inputToString = { (array, target) -> (array.toList() to target).toString() },
)

fun threeSumClosest(nums: IntArray, target: Int): Int {
    nums.sort()
    var best = nums[0] + nums[1] + nums[2]

    for (i in 0..(nums.lastIndex - 2)) {
        val bestForCurrent = twoSumClosest(nums, target - nums[i], i + 1)
        if (abs(target - (bestForCurrent + nums[i])) < abs(target - best)) {
            best = bestForCurrent + nums[i]
        }
    }

    return best
}

fun twoSumClosest(nums: IntArray, target: Int, startPosition: Int): Int {
    var l = startPosition
    var r = nums.lastIndex
    var best = nums[l] + nums[r]

    while (l < r) {
        val currentSum = nums[l] + nums[r]
        if (currentSum == target) {
            return currentSum
        } else {
            if (abs(target - currentSum) < abs(target - best)) {
                best = currentSum
            }
            if (currentSum > target) {
                r--
            } else {
                l++
            }
        }
    }

    return best
}
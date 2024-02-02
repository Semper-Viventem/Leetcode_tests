package algorithms

import utils.test

fun main() {
    test(
        testData = listOf(
            (intArrayOf(3) to intArrayOf(-2, -1)) to -1.0,
            (intArrayOf(1, 3) to intArrayOf(2)) to 2.0,
            (intArrayOf(1, 3) to intArrayOf(2, 4)) to 2.5,
            (intArrayOf() to intArrayOf(2, 4)) to 3.0,
            (intArrayOf(1) to intArrayOf()) to 1.0
        ),
        testFunctionExecution = { getMedianOfTwoArrays(it.first, it.second) }
    )
}

fun getMedianOfTwoArrays(nums1: IntArray, nums2: IntArray): Double {

    val isEven = (nums1.lastIndex + nums2.lastIndex) % 2 == 0
    val medianIndex: Int = Math.ceil((nums1.lastIndex + nums2.lastIndex) / 2.0).toInt()
    var currentArray1Index: Int? = if (nums1.isNotEmpty()) 0 else null
    var currentArray2Index: Int? = if (nums2.isNotEmpty()) 0 else null

    var currentValue: Double = -1.0

    for (i in 0..medianIndex) {
        var val1 = currentArray1Index?.let { nums1[it] } ?: Int.MAX_VALUE
        var val2 = currentArray2Index?.let { nums2[it] } ?: Int.MAX_VALUE

        if (val1 < val2) {
            currentValue = val1.toDouble()
            if (currentArray1Index == nums1.lastIndex) {
                currentArray1Index = null
            } else if (currentArray1Index != null) {
                currentArray1Index++
            }
        } else {
            currentValue = val2.toDouble()
            if (currentArray2Index == nums2.lastIndex) {
                currentArray2Index = null
            } else if (currentArray2Index != null) {
                currentArray2Index++
            }
        }

        if (isEven && i == medianIndex) {

            val1 = currentArray1Index?.let { nums1[it] } ?: Int.MAX_VALUE
            val2 = currentArray2Index?.let { nums2[it] } ?: Int.MAX_VALUE

            currentValue = (currentValue + Math.min(val1, val2)) / 2.0
        }
    }

    return currentValue
}
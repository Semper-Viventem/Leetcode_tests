package algorithms

import utils.test
import java.util.*

/**
 *
 */
fun main() {
    test(
        testData = listOf(
            listOf(6, 10, 2, 6, 5, 0, 6, 3, 1, 0, 0) to listOf(6.0, 8.0, 6.0, 6.0, 6.0, 5.5, 6.0, 5.5, 5.0, 4.0, 3.0),
            listOf(40, 12, 16) to listOf(40.0, 26.0, 16.0)
        ),
        testFunctionExecution = { input ->
            val medianFinder = MedianFinder()
            val result = mutableListOf<Double>()
            input.forEach {
                medianFinder.addNum(it)
                result.add(medianFinder.findMedian())
            }
            result
        },
        runOnlyCaseNr = null
    )
}

class MedianFinder() {

    var left = PriorityQueue<Int> { o1, o2 -> o2 - o1 }
    var mid: Int? = null
    var right = PriorityQueue<Int>()

    fun addNum(num: Int) {
        when {
            mid == null && left.isEmpty() && right.isEmpty() -> mid = num
            mid == null && left.isNotEmpty() && left.peek() >= num -> {
                val tmp = left.poll()
                left.offer(num)
                mid = tmp
            }

            mid == null && right.isNotEmpty() && right.peek() <= num -> {
                val tmp = right.poll()
                right.offer(num)
                mid = tmp
            }

            mid == null && right.isNotEmpty() && right.peek() >= num && left.isNotEmpty() && left.peek() <= num -> {
                mid = num
            }

            mid != null -> {
                if (mid!! > num) {
                    right.offer(mid)
                    left.offer(num)
                } else {
                    right.offer(num)
                    left.offer(mid)
                }
                mid = null
            }
        }
    }

    fun findMedian(): Double {
        return if (mid != null) {
            mid!!.toDouble()
        } else {
            (left.peek() + right.peek()) / 2.0
        }
    }
}
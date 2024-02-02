package algorithms

import utils.test
import kotlin.math.max
import kotlin.math.min

/**
 * Let's say we have an array of size N, where i'th element is the price of stock at day i.
 *
 * If you were only allowed to complete at most one transaction (buy one stock and sell it later), design an algorithm to maximize the profit.
 *
 * Example:
 * Input: [6,1,5,2,6,3]
 * Output: 5
 *
 * Explanation: you can buy it on day 1, when price is 1, and sell on day 4, when price is 6.
 */
fun main() = test(
    testData = listOf(
        (intArrayOf(6, 1, 5, 2, 6, 3) to 1) to 5,
        (intArrayOf(6, 5, 2, 1, 3, 6) to 1) to 5,
        (intArrayOf(6, 1, 5, 2, 6, 3) to 2) to 8,
        (intArrayOf(6, 2, 6, 3, 7) to 2) to 8,
        (intArrayOf(6, 1, 5, 2, 6, 3, 7) to 3) to 12,
        (intArrayOf(6, 1, 5, 9, 11, 11, 15) to 3) to 14,
    ),
    testFunctionExecution = { (days, k) -> maxProfit1(k, days) },
    inputToString = { (it.first.toList() to it.second).toString() },
    runOnlyCaseNr = 5
)

fun maxProfit(k: Int, prices: IntArray): Int {
    val buy = IntArray(k) { Int.MIN_VALUE }
    val sell = IntArray(k)
    for (price in prices) {
        for (i in 0..<k) {
            buy[i] = maxOf(buy[i], sell.getOrElse(i - 1) { 0 } - price)
            sell[i] = maxOf(sell[i], buy[i] + price)
        }
    }
    return sell[k - 1]
}

/**
 * Time: O(n * k)
 * Space: O(n * k) ??
 */
fun maxProfit1(k: Int, prices: IntArray): Int {
    if (prices.size < k) return 0
    val dp = Array(prices.size) { IntArray(k) { -1 } }
    return solution(prices, k, 0, dp)
}

private fun solution(days: IntArray, k: Int, startIndex: Int, dp: Array<IntArray>): Int {
    if (dp[startIndex][k - 1] != -1) {
        return dp[startIndex][k - 1]
    }

    var minPrice = days[startIndex]

    for (i in startIndex..days.lastIndex) {
        minPrice = min(minPrice, days[i])
        if (k > 1 && i < days.lastIndex) {
            dp[startIndex][k - 1] = max(dp[startIndex][k - 1], (days[i] - minPrice) + solution(days, k - 1, i + 1, dp))
        } else {
            dp[startIndex][k - 1] = max(dp[startIndex][k - 1], days[i] - minPrice)
        }
    }

    return dp[startIndex][k - 1]
}
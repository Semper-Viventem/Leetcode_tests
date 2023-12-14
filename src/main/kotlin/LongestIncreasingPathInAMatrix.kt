/**
 * Given an m x n integers matrix, return the length of the longest increasing path in matrix.
 *
 * From each cell, you can either move in four directions: left, right, up, or down. You may not move diagonally or move outside the boundary (i.e., wrap-around is not allowed).
 *
 *
 *
 * Example 1:
 *
 * Input: matrix = [[9,9,4],[6,6,8],[2,1,1]]
 * Output: 4
 * Explanation: The longest increasing path is [1, 2, 6, 9].
 *
 * Example 2:
 *
 * Input: matrix = [[3,4,5],[3,2,6],[2,2,1]]
 * Output: 4
 * Explanation: The longest increasing path is [3, 4, 5, 6]. Moving diagonally is not allowed.
 *
 * Example 3:
 *
 * Input: matrix = [[1]]
 * Output: 1
 *
 * https://leetcode.com/problems/longest-increasing-path-in-a-matrix/
 */
fun main() = test(
    testData = listOf(
        arrayOf(
            intArrayOf(9, 9, 4),
            intArrayOf(6, 6, 8),
            intArrayOf(2, 1, 1),
        ) to 4,
        arrayOf(
            intArrayOf(3, 4, 5),
            intArrayOf(3, 2, 6),
            intArrayOf(2, 2, 1),
        ) to 4,
        arrayOf(
            intArrayOf(1),
        ) to 1,
    ),
    inputToString = ::arrayToString,
    testFunctionExecution = ::longestIncreasingPath
)

fun longestIncreasingPath(matrix: Array<IntArray>): Int {

    var result: Int = 1
    val dp = Array(matrix.size) { IntArray(matrix[0].size) { -1 } }

    fun calculate(row: Int, column: Int): Int {
        if (dp[row][column] != -1) return dp[row][column]

        var localResult = 1

        val current = matrix[row][column]

        var bestDirection = 0
        // top
        if (row > 0 && matrix[row - 1][column] > current) {
            val direction = calculate(row - 1, column)
            if (direction > bestDirection) {
                bestDirection = direction
            }
        }

        // right
        if (column < matrix[0].lastIndex && matrix[row][column + 1] > current) {
            val direction = calculate(row, column + 1)
            if (direction > bestDirection) {
                bestDirection = direction
            }
        }

        // bottom
        if (row < matrix.lastIndex && matrix[row + 1][column] > current) {
            val direction = calculate(row + 1, column)
            if (direction > bestDirection) {
                bestDirection = direction
            }
        }

        // left
        if (column > 0 && matrix[row][column - 1] > current) {
            val direction = calculate(row, column - 1)
            if (direction > bestDirection) {
                bestDirection = direction
            }
        }

        localResult += bestDirection

        dp[row][column] = localResult
        return localResult
    }

    for (row in matrix.indices) {
        for (column in matrix[0].indices) {
            val cellResult = calculate(row, column)
            if (cellResult > result) {
                result = cellResult
            }
        }
    }

    return result
}
package algorithms

import utils.arrayToString
import utils.test
import kotlin.math.max

fun main() = test(
    testData = listOf(
        arrayOf(
            intArrayOf(0, 6, 0),
            intArrayOf(5, 8, 7),
            intArrayOf(0, 9, 0),
        ) to 24,
    ),
    inputToString = ::arrayToString,
    testFunctionExecution = ::getMaximumGold,
)

fun getMaximumGold(grid: Array<IntArray>): Int {
    var result = 0

    for (row in grid.indices) {
        for (column in grid.first().indices) {
            val cellMax = findMaxPathsFromCell(grid, row, column)
            result = max(result, cellMax)
        }
    }

    return result
}

fun findMaxPathsFromCell(grid: Array<IntArray>, row: Int, column: Int): Int {
    if (grid[row][column] == 0) return 0

    var best = 0

    val originalValue = grid[row][column]

    grid[row][column] = 0
    // top
    if (row > 0) {
        val top = findMaxPathsFromCell(grid, row - 1, column)
        best = max(best, top)
    }

    // bottom
    if (row < grid.lastIndex) {
        val bottom = findMaxPathsFromCell(grid, row + 1, column)
        best = max(best, bottom)
    }

    // left
    if (column > 0) {
        val left = findMaxPathsFromCell(grid, row, column - 1)
        best = max(best, left)
    }

    // right
    if (column < grid.first().lastIndex) {
        val right = findMaxPathsFromCell(grid, row, column + 1)
        best = max(best, right)
    }

    grid[row][column] = originalValue
    return originalValue + best
}
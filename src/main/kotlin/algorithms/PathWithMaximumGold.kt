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

data class Cell(val row: Int, val column: Int)

fun getMaximumGold(grid: Array<IntArray>): Int {
    var result = 0

    for (row in grid.indices) {
        for (column in grid.first().indices) {
            val cellMax = findMaxPathsFromCell(grid, setOf(), Cell(row, column))
            result = max(result, cellMax)
        }
    }

    return result
}

fun findMaxPathsFromCell(grid: Array<IntArray>, backtracking: Set<Cell>, cell: Cell): Int {
    if (grid[cell.row][cell.column] == 0 || backtracking.contains(cell)) return 0

    var best = 0

    val nextBackTracking = backtracking + setOf(Cell(cell.row, cell.column))

    // top
    if (cell.row > 0) {
        val top = findMaxPathsFromCell(grid, nextBackTracking, Cell(cell.row - 1, cell.column))
        best = max(best, top)
    }

    // bottom
    if (cell.row < grid.lastIndex) {
        val bottom = findMaxPathsFromCell(grid, nextBackTracking, Cell(cell.row + 1, cell.column))
        best = max(best, bottom)
    }

    // left
    if (cell.column > 0) {
        val left = findMaxPathsFromCell(grid, nextBackTracking, Cell(cell.row, cell.column - 1))
        best = max(best, left)
    }

    // right
    if (cell.column < grid.first().lastIndex) {
        val right = findMaxPathsFromCell(grid, nextBackTracking, Cell(cell.row, cell.column + 1))
        best = max(best, right)
    }

    return grid[cell.row][cell.column] + best
}
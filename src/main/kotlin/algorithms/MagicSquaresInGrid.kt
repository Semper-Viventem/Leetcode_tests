package algorithms

import utils.arrayToString
import utils.test

/**
 *
 * 840. Magic Squares In Grid
 *
 * A 3 x 3 magic square is a 3 x 3 grid filled with distinct numbers from 1 to 9 such that each row, column, and both diagonals all have the same sum.
 *
 * Given a row x col grid of integers, how many 3 x 3 contiguous magic square subgrids are there?
 *
 * Note: while a magic square can only contain numbers from 1 to 9, grid may contain numbers up to 15.
 *
 * Example 1:
 *
 * Input: grid = [[4,3,8,4],[9,5,1,9],[2,7,6,2]]
 * Output: 1
 *
 * Example 2
 *
 * Input: grid = [[8]]
 * Output: 0
 *
 * @see https://leetcode.com/problems/magic-squares-in-grid/description/?envType=daily-question&envId=2024-08-09
 *
 */
fun main() = test(
    testData = listOf(
        arrayOf(
            intArrayOf(4,3,8,4),
            intArrayOf(9,5,1,9),
            intArrayOf(2,7,6,2),
        ) to 1
    ),
    testFunctionExecution = ::numMagicSquaresInside,
    inputToString = ::arrayToString,
)

fun numMagicSquaresInside(grid: Array<IntArray>): Int {

    if (grid.size < 3 || grid.first().size < 3) {
        return 0
    }

    var result = 0

    for (row in (1..<grid.lastIndex)) {
        for (column in (1..<grid.first().lastIndex)) {
            val anchorValue = grid[row][column]

            if (anchorValue == 5 && checkSquare(grid, row, column)) {
                result++
            }
        }
    }

    return result
}

fun checkSquare(grid: Array<IntArray>, centerRow: Int, centerColumn: Int): Boolean {

    // check rows summ
    for (row in (centerRow - 1)..(centerRow + 1)) {
        if (grid[row][centerColumn - 1] + grid[row][centerColumn] + grid[row][centerColumn + 1] != 15) {
            return false
        }
    }

    // check columns summ
    for (column in (centerColumn - 1)..(centerColumn + 1)) {
        if (grid[centerRow - 1][column] + grid[centerRow][column] + grid[centerRow + 1][column] != 15) {
            return false
        }
    }

    // check set
    val set = mutableSetOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    for (row in (centerRow - 1)..(centerRow + 1)) {
        for (column in (centerColumn - 1)..(centerColumn + 1)) {
            val current = grid[row][column]

            if (!set.contains(current)) {
                return false
            } else {
                set.remove(current)
            }
        }
    }

    return set.isEmpty()
}
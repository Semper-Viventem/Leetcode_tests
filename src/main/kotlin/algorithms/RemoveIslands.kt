package algorithms

import utils.test

/**
 *
 * There is a matrix of 0/1 items.
 * Need to remove all Ones islands in the matrix somehow connected to the border.
 * Connected only vertically or horizontally.
 *
 * Example:
 *   Input:
 *     [1, 0, 0, 0, 0, 0]
 *     [0, 1, 0, 1, 1, 1]
 *     [0, 0, 1, 0, 1, 0]
 *     [1, 1, 0, 0, 1, 0]
 *     [1, 0, 1, 1, 0, 0]
 *     [1, 0, 0, 0, 0, 1]
 *
 *   Output:
 *     [1, 0, 0, 0, 0, 0]
 *     [0, 0, 0, 1, 1, 1]
 *     [0, 0, 0, 0, 1, 0]
 *     [1, 1, 0, 0, 1, 0]
 *     [1, 0, 0, 0, 0, 0]
 *     [1, 0, 0, 0, 0, 1]
 *
 */
fun main() = test(
    testData = listOf(
        listOf(
            listOf(1, 0, 0, 0, 0, 0),
            listOf(0, 1, 0, 1, 1, 1),
            listOf(0, 0, 1, 0, 1, 0),
            listOf(1, 1, 0, 0, 1, 0),
            listOf(1, 0, 1, 1, 0, 0),
            listOf(1, 0, 0, 0, 0, 1),
        ) to listOf(
            listOf(1, 0, 0, 0, 0, 0),
            listOf(0, 0, 0, 1, 1, 1),
            listOf(0, 0, 0, 0, 1, 0),
            listOf(1, 1, 0, 0, 1, 0),
            listOf(1, 0, 0, 0, 0, 0),
            listOf(1, 0, 0, 0, 0, 1),
        ),
        listOf(
            listOf(0, 0, 0, 0, 0, 0),
            listOf(0, 1, 0, 1, 1, 0),
            listOf(0, 0, 1, 0, 1, 0),
            listOf(0, 1, 0, 0, 1, 0),
            listOf(0, 0, 1, 1, 0, 0),
            listOf(0, 0, 0, 0, 0, 0),
        ) to listOf(
            listOf(0, 0, 0, 0, 0, 0),
            listOf(0, 0, 0, 0, 0, 0),
            listOf(0, 0, 0, 0, 0, 0),
            listOf(0, 0, 0, 0, 0, 0),
            listOf(0, 0, 0, 0, 0, 0),
            listOf(0, 0, 0, 0, 0, 0),
        )
    ),
    testFunctionExecution = ::removeIslands
)

/**
 * Time complexity: O(n)
 * Space complexity: O(n)
 */
fun removeIslands(matrix: List<List<Int>>): List<List<Int>> {
    val rowMax = matrix.lastIndex
    val columnMax = matrix[0].lastIndex

    val isConnected = hashSetOf<Pair<Int, Int>>()

    fun checkNode(rowIndex: Int, columnIndex: Int) {
        if (matrix[rowIndex][columnIndex] == 0 || isConnected.contains(rowIndex to columnIndex)) return

        isConnected.add(rowIndex to columnIndex)

        // check top
        if (rowIndex > 0) {
            checkNode(rowIndex - 1, columnIndex)
        }

        // check right
        if (columnIndex < columnMax) {
            checkNode(rowIndex, columnIndex + 1)
        }

        // check bottom
        if (rowIndex < rowMax) {
            checkNode(rowIndex + 1, columnIndex)
        }

        // check right
        if (columnIndex > 0) {
            checkNode(rowIndex, columnIndex - 1)
        }
    }

    matrix.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { columnIndex, _ ->
            if (rowIndex == 0 || rowIndex == rowMax || columnIndex == 0 || columnIndex == columnMax) {
                checkNode(rowIndex, columnIndex)
            }
        }
    }

    return matrix.mapIndexed { rowIndex, row ->
        row.mapIndexed { columnIndex, item ->

            if (rowIndex == 0 || rowIndex == rowMax || columnIndex == 0 || columnIndex == columnMax) {
                checkNode(rowIndex, columnIndex)
            }

            if (isConnected.contains(rowIndex to columnIndex)) item else 0
        }
    }
}
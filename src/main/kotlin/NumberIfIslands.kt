/**
 *Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), return the number of islands.
 *
 * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.
 *
 *
 *
 * Example 1:
 *
 * Input: grid = [
 *   ["1","1","1","1","0"],
 *   ["1","1","0","1","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","0","0","0"]
 * ]
 * Output: 1
 *
 * Example 2:
 *
 * Input: grid = [
 *   ["1","1","0","0","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","1","0","0"],
 *   ["0","0","0","1","1"]
 * ]
 * Output: 3
 *
 */
fun main() = test(
    testData = listOf(
        arrayOf(
            charArrayOf('1', '1', '1', '1', '0'),
            charArrayOf('1', '1', '0', '1', '0'),
            charArrayOf('1', '1', '0', '0', '0'),
            charArrayOf('0', '0', '0', '0', '0'),
        ) to 1,
        arrayOf(
            charArrayOf('1', '1', '0', '0', '0'),
            charArrayOf('1', '1', '0', '0', '0'),
            charArrayOf('0', '0', '1', '0', '0'),
            charArrayOf('0', '0', '0', '1', '1'),
        ) to 3,
    ),
    testFunctionExecution = ::numIslands,
)

/**
 * Time complexity: O(n)
 * Space complexity: O(n)
 */
fun numIslands(grid: Array<CharArray>): Int {
    val rowMax = grid.lastIndex
    val columnMax = grid[0].lastIndex

    val markedIslands = hashSetOf<Pair<Int, Int>>()
    var count = 0

    fun checkNode(rowIndex: Int, columnIndex: Int, islandEntryPoint: Pair<Int, Int>) {
        if (grid[rowIndex][columnIndex] == '0' || markedIslands.contains(rowIndex to columnIndex)) return

        markedIslands.add(rowIndex to columnIndex)

        // check top
        if (rowIndex > 0) {
            checkNode(rowIndex - 1, columnIndex, islandEntryPoint)
        }

        // check right
        if (columnIndex < columnMax) {
            checkNode(rowIndex, columnIndex + 1, islandEntryPoint)
        }

        // check bottom
        if (rowIndex < rowMax) {
            checkNode(rowIndex + 1, columnIndex, islandEntryPoint)
        }

        // check right
        if (columnIndex > 0) {
            checkNode(rowIndex, columnIndex - 1, islandEntryPoint)
        }
    }

    for (rowIndex in 0..rowMax) {
        for (columnIndex in 0..columnMax) {
            if (grid[rowIndex][columnIndex] == '1' && !markedIslands.contains(rowIndex to columnIndex)) {
                checkNode(rowIndex, columnIndex, islandEntryPoint = rowIndex to columnIndex)
                count++
            }
        }
    }

    return count
}
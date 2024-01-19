import kotlin.math.min

fun main() = test(
    testData = listOf(
        arrayOf(
            intArrayOf(2, 1, 3),
            intArrayOf(6, 5, 4),
            intArrayOf(7, 8, 9),
        ) to 13,
        arrayOf(
            intArrayOf(-19, 57),
            intArrayOf(-40, -5),
        ) to -59,
    ),
    testFunctionExecution = ::minFallingPathSum,
    inputToString = { it.joinToString(separator = "\n") { it.toList().toString() } }
)

fun minFallingPathSum(matrix: Array<IntArray>): Int {
    var result: Int = Int.MAX_VALUE
    val dp: Array<Array<Int?>> = Array(matrix.size) { Array(matrix[0].size) { null } }
    for (i in 0..matrix[0].lastIndex) {
        result = min(result, minFailingPathFromCell(matrix, i, 0, dp))
    }
    return result
}

fun minFailingPathFromCell(
    matrix: Array<IntArray>,
    anchorX: Int,
    anchorY: Int,
    dp: Array<Array<Int?>>,
): Int {
    if (dp[anchorY][anchorX] != null) {
        return dp[anchorY][anchorX]!!
    }

    var current = matrix[anchorY][anchorX]

    if (anchorY != matrix.lastIndex) {
        var addition: Int

        addition = minFailingPathFromCell(matrix, anchorX, anchorY + 1, dp)

        if (anchorX > 0) {
            addition = min(addition, minFailingPathFromCell(matrix, anchorX - 1, anchorY + 1, dp))
        }

        if (anchorX < matrix[0].lastIndex) {
            addition = min(addition, minFailingPathFromCell(matrix, anchorX + 1, anchorY + 1, dp))
        }

        current += addition
    }

    dp[anchorY][anchorX] = current
    return current
}
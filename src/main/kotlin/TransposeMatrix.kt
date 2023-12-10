/**
 * Given a 2D integer array matrix, return the transpose of matrix.
 *
 * The transpose of a matrix is the matrix flipped over its main diagonal, switching the matrix's row and column indices.
 *
 *
 * Example 1:
 *
 * Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
 * Output: [[1,4,7],[2,5,8],[3,6,9]]
 *
 * Example 2:
 *
 * Input: matrix = [[1,2,3],[4,5,6]]
 * Output: [[1,4],[2,5],[3,6]]
 *
 * https://leetcode.com/problems/transpose-matrix/?envType=daily-question&envId=2023-12-10
 *
 */
fun main() = test(
    testData = listOf(
        arrayOf(
            intArrayOf(1, 2, 3),
            intArrayOf(4, 5, 6),
            intArrayOf(7, 8, 9),
        ) to arrayOf(
            intArrayOf(1, 4, 7),
            intArrayOf(2, 5, 8),
            intArrayOf(3, 6, 9),
        ),
        arrayOf(
            intArrayOf(1, 2, 3),
            intArrayOf(4, 5, 6),
        ) to arrayOf(
            intArrayOf(1, 4),
            intArrayOf(2, 5),
            intArrayOf(3, 6),
        ),
        arrayOf(
            intArrayOf(7, 2),
        ) to arrayOf(
            intArrayOf(7),
            intArrayOf(2),
        ),
        arrayOf(
            intArrayOf(5),
            intArrayOf(8),
        ) to arrayOf(
            intArrayOf(5, 8),
        )
    ),
    testFunctionExecution = ::transpose,
    inputToString = ::arrayToString,
    outputToString = ::arrayToString,
    assert = { expected, actual -> arrayToString(expected) == arrayToString(actual) }
)

private fun arrayToString(array: Array<IntArray>): String {
    val result = StringBuffer()
    result.append("\n")
    result.append(
        array.joinToString(prefix = "[\n", postfix = "\n]\n", separator = ",\n") {
            it.joinToString(prefix = "  [", postfix = "]", separator = ", ")
        }
    )
    return result.toString()
}

fun transpose(matrix: Array<IntArray>): Array<IntArray> {
    return Array(matrix[0].size) { i ->
        IntArray(matrix.size) { j -> matrix[j][i] }
    }
}
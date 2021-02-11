/**
 * https://leetcode.com/problems/zigzag-conversion
 */
fun main() {
    val data = listOf(
        ("PAYPALISHIRING" to 2) to "PYAIHRNAPLSIIG",
        ("A" to 1) to "A",
        ("PAYPALISHIRING" to 3) to "PAHNAPLSIIGYIR",
        ("PAYPALISHIRING" to 4) to "PINALSIGYAHRPI"
    )

    test(
        testData = data,
        testFunctionExecution = { convert(it.first, it.second) }
    )
}

fun convert(s: String, numRows: Int): String {
    if (numRows == 1) return s

    val diagonalLength = maxOf(numRows - 2, 0)
    val zigZagLength = numRows + diagonalLength
    val countOfZigZag: Double = s.length / zigZagLength.toDouble()

    return buildString {
        for (row in 0 until numRows) {
            for (i in 0..Math.ceil(countOfZigZag).toInt()) {
                val symbolIndex = ((zigZagLength) * i) + row

                if (row in 1 until (numRows - 1) && i > 0) {
                    s.getOrNull(symbolIndex - (row * 2))?.let { append(it) } ?: break
                }

                s.getOrNull(symbolIndex)?.let { append(it) } ?: break
            }
        }
    }
}
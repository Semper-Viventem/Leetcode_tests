fun main() {
    test(
        testData = listOf(
            intArrayOf(70, 100, 50) to 120,
            intArrayOf(1, 20, 1) to 20,
            intArrayOf(10, 1, 1, 100) to 110,
            intArrayOf(10, 20, 1, 100, 3) to 120,
            intArrayOf(10, 100, 1, 50, 100) to 200,
            intArrayOf() to 0,
            intArrayOf(50) to 50,
            intArrayOf(0, 0, 0, 0) to 0,
            intArrayOf(0, 0, 1, 0) to 1,
            intArrayOf(0, 0, 1, 1) to 1,
            intArrayOf(0, 1, 1, 1) to 2,
            intArrayOf(1, 1, 1, 1) to 2,
            intArrayOf(60, 90, 60, 60, 90, 60) to 210,
            intArrayOf(100, 1, 100, 90, 1, 90, 95, 1) to 296,
            intArrayOf(90, 100, 90, 100, 90) to 270
        ),
        testFunctionExecution = ::maxLogs
    )
}

fun maxLogs(logsCount: IntArray): Int {
    if (logsCount.isEmpty()) return 0
    var maxResult = 0

    val logsCountList = logsCount.toMutableList()
    logsCountList.forEachIndexed { index, item ->
        val logsWithExcluded =
            logsCountList.mapIndexedNotNull { exclusionIndex, it -> if (exclusionIndex == index + 1 || exclusionIndex == index - 1 || exclusionIndex == index) null else it }

        val newResult = maxLogs(logsWithExcluded.toIntArray()) + item
        maxResult = Math.max(newResult, maxResult)
    }

    return maxResult
}
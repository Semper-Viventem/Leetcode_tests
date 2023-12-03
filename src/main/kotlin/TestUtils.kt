fun <A, B> test(
    testData: List<Pair<A, B>>,
    testFunctionExecution: (input: A) -> B,
    runOnlyCaseNr: Int? = null
) {
    var failedCount: Int = 0

    val selectedTests = if (runOnlyCaseNr != null) {
        listOf(testData[runOnlyCaseNr])
    } else {
        testData
    }

    selectedTests.forEachIndexed { index, (test, expected) ->
        if (test is IntArray) {
            println(test.toList())
        } else {
            println(test)
        }
        val result = testFunctionExecution.invoke(test)
        val isPassed = expected == result
        if (!isPassed) {
            failedCount++
        }
        print("[$index] ")
        println(if (isPassed) "SUCCESS" else "FAILED (output: $result expected: $expected)")
        println()
    }

    println(if (failedCount == 0) "TOTAL: SUCCESS" else "TOTAL: FAILED ($failedCount)")
    println()
}
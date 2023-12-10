fun <A, B> test(
    testData: List<Pair<A, B>>,
    testFunctionExecution: (input: A) -> B,
    runOnlyCaseNr: Int? = null,
    inputToString: (input: A) -> String = ::defaultDataTo,
    outputToString: (input: B) -> String = ::defaultDataTo,
    assert: (expected: B, actual: B) -> Boolean = { expected, actual -> expected == actual }
) {
    var failedCount: Int = 0

    val selectedTests = if (runOnlyCaseNr != null) {
        listOf(testData[runOnlyCaseNr])
    } else {
        testData
    }

    selectedTests.forEachIndexed { index, (test, expected) ->
        println(inputToString(test))
        val result = testFunctionExecution.invoke(test)
        val isPassed = assert(expected, result)
        if (!isPassed) {
            failedCount++
        }
        print("[$index] ")
        println(if (isPassed) "SUCCESS" else "FAILED (output: ${outputToString(result)} expected: ${outputToString(expected)})")
        println()
    }

    println(if (failedCount == 0) "TOTAL: SUCCESS" else "TOTAL: FAILED ($failedCount)")
    println()
}

private fun defaultDataTo(data: Any?): String {
    return if (data is IntArray) {
        data.toList().toString()
    } else {
        data.toString()
    }
}
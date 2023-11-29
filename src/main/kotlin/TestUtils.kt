fun <A, B> test(
    testData: List<Pair<A, B>>,
    testFunctionExecution: (input: A) -> B,
    runOnlyCaseNr: Int? = null
) {
    var isAllPassed = true

    val selectedTests = if (runOnlyCaseNr != null) {
        listOf(testData[runOnlyCaseNr])
    } else {
        testData
    }

    selectedTests.forEach { (test, expected) ->
        if (test is IntArray) {
            println(test.toList())
        } else {
            println(test)
        }
        val result = testFunctionExecution.invoke(test)
        val isPassed = expected == result
        if (!isPassed) {
            isAllPassed = false
        }
        println(if (isPassed) "SUCCESS" else "FAILED (output: $result expected: $expected)")
        println()
    }

    println(if (isAllPassed) "TOTAL: SUCCESS" else "TOTAL: FAILED")
    println()
}
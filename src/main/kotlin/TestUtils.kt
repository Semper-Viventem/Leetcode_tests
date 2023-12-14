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

data class ListNode(var `val`: Int, var next: ListNode? = null) {
    override fun toString(): String {
        val builder = StringBuilder()
        var current: ListNode? = this
        builder.append("[")
        while (true) {
            builder.append(current?.`val`)
            if (current?.next == null) {
                builder.append("]")
                break
            } else {
                builder.append(", ")
            }
            current = current?.next
        }
        return builder.toString()
    }
}

fun IntArray.toListNode(): ListNode? {
    if (isEmpty()) return null
    var lastNode = ListNode(get(lastIndex))
    for (i in (lastIndex - 1) downTo 0) {
        val tmp = ListNode(get(i)).apply { next = lastNode }
        lastNode = tmp
    }
    return lastNode
}

fun arrayToString(array: Array<IntArray>): String {
    val result = StringBuffer()
    result.append("\n")
    result.append(
        array.joinToString(prefix = "[\n", postfix = "\n]\n", separator = ",\n") {
            it.joinToString(prefix = "  [", postfix = "]", separator = ", ")
        }
    )
    return result.toString()
}
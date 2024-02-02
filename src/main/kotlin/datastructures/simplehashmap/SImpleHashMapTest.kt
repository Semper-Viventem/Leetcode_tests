package datastructures.simplehashmap

import utils.test

fun main() {
    testInternal(
        testCase("1", testName = "Add one item") { map ->
            map.put(0, "1")
            map.get(0)
        },
        testCase(null, testName = "Add and remove one item") { map ->
            map.put(0, "1")
            map.put(0, null)
            map.get(0)
        },
        testCase(16, testName = "Don't call resize if threshold is not reached") { map ->
            repeat(11) {
                map.put(it, "test")
            }
            map.capacity
        },
        testCase(32, testName = "Call resize if threshold is reached") { map ->
            repeat(12) {
                map.put(it, "test")
            }
            map.capacity
        },
        testCase(listOf("1", "2", "3"), testName = "Check several items added") { map ->
            map.put(0, "1")
            map.put(1, "2")
            map.put(2, "3")

            listOf(map.get(0), map.get(1), map.get(2))
        },
        testCase(listOf("1", "4", "3"), testName = "Check one item has been changed") { map ->
            map.put(0, "1")
            map.put(1, "2")
            map.put(2, "3")

            map.put(1, "4")

            listOf(map.get(0), map.get(1), map.get(2))
        },
    )
}

private fun testInternal(
    vararg testCases: TestCase<Any, Any, Any>
) {

    val testData = testCases.map { testCase ->
        testCase to testCase.expectedResult
    }
    test(
        testData = testData,
        testFunctionExecution = { input ->
            input.executionFunction.invoke(input.buildMap())
        }
    )
}

private class TestCase<K, V, Result>(
    val expectedResult: Result?,
    val testName: String = "TestCase(expectedResult=${expectedResult.toString()})",
    val executionFunction: (map: SimpleHashMap<K, V>) -> Result?,
) {
    fun buildMap(): SimpleHashMap<K, V> {
        return SimpleHashMap()
    }

    override fun toString(): String {
        return testName
    }
}

private fun <K, V, Result> testCase(
    expectedResult: Result?,
    testName: String = "TestCase(expectedResult=${expectedResult.toString()})",
    executionFunction: (map: SimpleHashMap<K, V>) -> Result?,
): TestCase<K, V, Result> {
    return TestCase(expectedResult, testName, executionFunction)
}
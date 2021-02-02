fun <A, B> test(testData: List<Pair<A, B>>, testFunctionExecution: (input: A) -> B) {
    testData.forEach { (test, expected) ->
        if (test is IntArray) {
            println(test.toList())
        } else {
            println(test)
        }
        val result = testFunctionExecution.invoke(test)
        println(if (expected == result) "OK" else "FUCK ($result)")
        println()
    }
}
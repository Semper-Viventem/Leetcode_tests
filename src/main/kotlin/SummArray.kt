fun main() {
    val testData = """
66
10
18
11
21
28
31
38
40
55
60
62
    """.trimIndent()

    println(testData)
    println()
    println(sumArray(testData))
}

fun sumArray(testData: String): String {
    var founded = false
    val input = testData.split("\n").map { it.toInt() }

    val sum = input[0]
    val size = input[1]
    val numbers = IntArray(size) { input[it + 2] }

    numbers.forEach { number ->
        val diff = sum - number
        if (numbers.contains(diff)) {
            founded = true
            return@forEach
        }
    }

    return if (founded) "1" else "0"
}
package algorithms

import kotlin.math.abs

fun main() {
    val testData = "25626 25757 24367 24267 16 100 2 7277"

    println(testData)
    println(delta(testData))
}

private const val ESCAPE_TOKEN = -128
fun delta(testData: String): String {
    val values = testData.split(" ").map { it.toInt() }

    val result = mutableListOf<Int>()
    var prev = values[0]
    result.add(prev)
    for (i in 1..values.lastIndex) {
        val diff = values[i] - prev
        if (abs(diff) > 127) {
            result.add(ESCAPE_TOKEN)
        }
        result.add(diff)
        prev = values[i]
    }

    return result.joinToString(separator = " ") { it.toString() }
}
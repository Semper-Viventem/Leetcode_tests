package algorithms

fun main() {
    val tests = listOf(
        listOf(2, 3, 4, 5, 6) to 1,
        listOf(1, 3, 4, 5, 6) to 2,
        listOf(1, 2, 4, 5, 6) to 3,
        listOf(1, 2, 3, 5, 6) to 4,
        listOf(1, 2, 3, 4, 6) to 5,
        listOf(1, 2, 3, 4, 5) to 6,
    )

    tests.forEach { (test, expected) ->
        val result = missingLog(test)
        println(test)
        println(if (expected == result) "OK" else "FUCK ($result)")
        println()
    }
}

fun missingLog(sortedLogIds: List<Int>): Int {
    var missed = sortedLogIds.last() + 1
    var prev = 0
    for (i in 0..sortedLogIds.lastIndex) {
        if (sortedLogIds[i] - prev != 1) {
            missed = sortedLogIds[i] - 1
            break
        }
        prev = sortedLogIds[i]
    }

    return missed
}
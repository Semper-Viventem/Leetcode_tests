package algorithms

fun main() {
    val testData = """
        pear
        amleth
        dormitory
        tinsel
        dirty room
        hamlet
        listen
        silnet
    """.trimIndent()

    println(testData)
    println()
    println(anagrams(testData))
}

fun anagrams(testData: String): String {
    val anagrams = testData.split("\n")

    val result: HashMap<List<Char>, MutableSet<String>> = hashMapOf()
    anagrams.forEach { word ->
        val chars = word.mapNotNullTo(mutableListOf()) {
            if (it == ' ') null else it
        }.sorted()
        result.getOrPut(chars, { mutableSetOf() }).add(word)
    }

    val stringResult = result.values.map { it.toList().sorted().joinToString(separator = ",") { it } }
    return stringResult.sorted().joinToString(separator = "\n") { it }
}
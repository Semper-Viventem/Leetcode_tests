fun main() {
    val tests = listOf(
        ":)" to listOf(InlinedEmoticon(1, 0, 2)),
        ":-)" to listOf(InlinedEmoticon(1, 0, 3)),
        ":/" to listOf(InlinedEmoticon(2, 0, 2)),
        ":(" to listOf(InlinedEmoticon(3, 0, 2)),
        ":-(" to listOf(InlinedEmoticon(3, 0, 3)),

        "12345::)" to listOf(InlinedEmoticon(1, 6, 2)),
        "How is it going? :) You are almost done :-)!" to listOf(InlinedEmoticon(1, 17, 2), InlinedEmoticon(1, 40, 3)),
        "How is it going? :-) You are almost done :-(!" to listOf(InlinedEmoticon(1, 17, 3), InlinedEmoticon(3, 41, 3)),
        "How is it going? :-) You are almost done :/!" to listOf(InlinedEmoticon(1, 17, 3), InlinedEmoticon(2, 41, 2)),
    )

    tests.forEach { (test, expected) ->
        val result = parseEmoticons(test)
        println(test)
        println(if (expected == result) "OK" else "FUCK ($result)")
        println()
    }
}

fun parseEmoticons(text: String): List<InlinedEmoticon> {
    val result = mutableListOf<InlinedEmoticon>()

    val emojiMap = hashMapOf(
        ":)" to (1 to 2),
        ":-)" to (1 to 3),
        ":/" to (2 to 2),
        ":(" to (3 to 2),
        ":-(" to (3 to 3)
    )

    for (i in 0..(text.lastIndex)) {
        val char = text[i]
        val char2 = text.getOrNull(i + 1) ?: break

        val emoji2 = "$char$char2"
        emojiMap.get(emoji2)?.let { (id, lenght) -> result.add(InlinedEmoticon(id, i, lenght)) }

        val char3 = text.getOrNull(i + 2) ?: break

        val emoji3 = "$char$char2$char3"
        emojiMap.get(emoji3)?.let { (id, lenght) -> result.add(InlinedEmoticon(id, i, lenght)) }
    }

    return result
}

data class InlinedEmoticon(val emoticonId: Int, val position: Int, val length: Int)
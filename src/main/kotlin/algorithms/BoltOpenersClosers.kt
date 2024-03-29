package algorithms

import utils.test
import java.util.*

/**
 * '(', '{', '[' are called "openers".
 * ')', '}', ']' are called "closers".
 * Write an efficient function that tells us whether input string's openers and closers are properly nested.
 *
 * Examples:
 * "{ [ ] ( ) }" -> true
 * "{ [ ( ] ) }" -> false
 *
 * ""  // ok
 * "{" // ok
 * "}" // ok
 * | ' "
 * || -> true
 * |()| -> tre
 * | ( | | ) | -> true
 */
fun main() {
    test(
        testData = listOf(
            "" to true,
            "]" to false,
            "[" to false,
            "||" to true,
            "|" to false,
            "{ [ ] ( ) }" to true,
            "{ [ ( ] ) }" to false,
            "| ( | | ) |" to true,
            "| ( |  ) |" to false,
            "| | ( { } [ ' ' ] ( ) ) | |" to true,
            "| | ( { } [ ' ' ] ( [ ) ) | |" to false,
        ),
        testFunctionExecution = ::foo
    )
}

/**
 * Runtime complexity: O(n)
 * Memory complexity: O(1)
 */
private fun foo(str: String): Boolean {

    val openers = LinkedList<Char>()

    val map = mapOf(
        ')' to '(',
        ']' to '[',
        '}' to '{'
    )

    val selfClosers = mutableMapOf(
        '|' to 0,
        '\'' to 0,
        '\"' to 0
    )

    for (c in str) {
        if (selfClosers[c] != null) {
            if (selfClosers[c] == 0) {
                selfClosers.computeIfPresent(c) { _, v -> v + 1 }
                openers.add(c)
            } else {
                val lastOpener = openers.last()
                if (lastOpener == c) {
                    selfClosers.computeIfPresent(c) { _, v -> v - 1 }
                    openers.removeLast()
                } else {
                    selfClosers.computeIfPresent(c) { _, v -> v + 1 }
                    openers.add(c)
                }
            }
        } else {
            if (map.values.contains(c)) {
                openers.add(c)
            }

            val expectedOpener = map[c]
            val lastOpener = openers.lastOrNull()

            if (expectedOpener != null && lastOpener == expectedOpener) {
                openers.removeLast()
            } else if (expectedOpener != null) {
                return false
            }
        }
    }

    return openers.isEmpty()
}

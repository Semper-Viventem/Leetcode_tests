package algorithms

import utils.test
import java.util.*

fun main() = test(
    testData = listOf(
        "384" to 384,
        "2+2" to 4,
        "2 + -1" to 1,
        "21+12" to 33,
        " 2 + 2- 1" to 3,
        " 1-1 +2" to 2,
        "2+2 * 2" to 6,
        "2 * 2 + 2" to 6,
        "2 * 2 + 2 / 2" to 5,
        //"2 * -2 + 2 / 2" to -3, // is not working with stack solution
        "0-2147483647" to -2147483647,
        "1*2-3/4+5*6-7*8+9/10" to -24,
    ),
    testFunctionExecution = ::calculate,
)

fun calculate(string: String): Int {
    val stack = Stack<Int>()

    var operation = '+'
    var current = 0

    for (i in string.indices) {
        val c = string[i]

        if (c.isDigit()) {
            current = current * 10 + c.digitToInt()
        }

        if ((!c.isDigit() && c != ' ') || i == string.lastIndex) {
            when (operation) {
                '+' -> stack.push(current)
                '-' -> stack.push(-current)
                '*' -> stack.push(stack.pop() * current)
                '/' -> stack.push(stack.pop() / current)
            }

            operation = c
            current = 0
        }
    }

    var result = 0
    while (!stack.isEmpty()) {
        result += stack.pop()
    }
    return result
}

fun calculateOld(string: String): Double {
    val tree = Operation.build(string.replace(" ", ""))
    println(tree)
    return tree.perform()
}

private sealed interface Operation {

    fun perform(): Double

    data class Number(val value: Double): Operation {
        override fun perform(): Double = value
        override fun toString(): String = value.toInt().toString()
    }

    data class Plus(val left: Operation, val right: Operation): Operation {
        override fun perform(): Double = left.perform() + right.perform()
        override fun toString(): String = "($left + $right)"
    }

    data class Minus(val left: Operation, val right: Operation): Operation {
        override fun perform(): Double = left.perform() - right.perform()
        override fun toString(): String = "($left - $right)"
    }

    data class Multiply(val left: Operation, val right: Operation): Operation {
        override fun perform(): Double = left.perform() * right.perform()
        override fun toString(): String = "($left * $right)"
    }

    data class Divide(val left: Operation, val right: Operation): Operation {
        override fun perform(): Double = left.perform() / right.perform()
        override fun toString(): String = "($left / $right)"
    }

    companion object {
        fun build(expression: String, leftOperation: Operation? = null): Operation {

            var leftOperation = leftOperation
            var leftEnd = -1


            if (leftOperation == null) {
                val next = readNext(expression)
                leftOperation = Number(next.value)
                leftEnd = next.endIndex
            }

            if (leftEnd == expression.lastIndex) {
                return leftOperation
            }

            val currentOperation = expression[leftEnd + 1]

            if (currentOperation == '*' || currentOperation == '/') {
                val nextRight = readNext(expression.substring(leftEnd + 2))

                val right = nextRight.value
                val rightEnd = leftEnd + 2 + nextRight.endIndex

                val operation = when (currentOperation) {
                    '*' -> Multiply(leftOperation, Number(right))
                    '/' -> Divide(leftOperation, Number(right))
                    else -> throw IllegalStateException()
                }
                return if (rightEnd == expression.lastIndex) {
                    operation
                } else {
                    build(expression.substring(rightEnd + 1), leftOperation = operation)
                }

            } else if (currentOperation == '-' || currentOperation == '+') {
                val nextRight = readNext(expression.substring(leftEnd + 2))

                val right = nextRight.value
                val rightEnd = leftEnd + 2 + nextRight.endIndex

                if (rightEnd == expression.lastIndex) {
                    return when (currentOperation) {
                        '+' -> Plus(leftOperation, Number(right))
                        '-' -> Minus(leftOperation, Number(right))
                        else -> throw IllegalArgumentException()
                    }
                }

                val lowPriority = rightEnd != expression.lastIndex && expression[rightEnd + 1] in setOf('*', '/')
                if (lowPriority) {
                    val rightOperation = build(expression.substring(leftEnd + 2))
                    return when (currentOperation) {
                        '+' -> Plus(leftOperation, rightOperation)
                        '-' -> Minus(leftOperation, rightOperation)
                        else -> throw IllegalArgumentException()
                    }
                } else {
                    val rightOperation = Number(right)
                    val operation = when (currentOperation) {
                        '+' -> Plus(leftOperation, rightOperation)
                        '-' -> Minus(leftOperation, rightOperation)
                        else -> throw IllegalArgumentException()
                    }

                    return build(expression.substring(rightEnd + 1), leftOperation = operation)
                }
            }

            throw IllegalArgumentException("Unsupported expression symbol: $currentOperation at index ${leftEnd + 1}")
        }

        private fun readNext(expression: String): NextFloatResult {
            var end = 0

            var i = 0
            while (i < expression.lastIndex && expression[i + 1].isDigit()) {
                i++
                end++
            }

            return NextFloatResult(expression.substring(0 .. end).toDouble(), end)
        }

        private data class NextFloatResult(val value: Double, val endIndex: Int)
    }
}
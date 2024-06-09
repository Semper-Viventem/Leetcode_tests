package algorithms

import utils.test

fun main() = test(
    testData = listOf(
        "384" to 384f,
        "2+2" to 4f,
        "2 + -1" to 1f,
        "21+12" to 33f,
        " 2 + 2- 1" to 3f,
        " 1-1 +2" to 2f,
        "2+2 * 2" to 6f,
        "2 * 2 + 2" to 6f,
        "2 * 2 + 2 / 2" to 5f,
        "2 * -2 + 2 / 2" to -3f,
    ),
    testFunctionExecution = ::mathExpression,
)

fun mathExpression(string: String): Float {
    return Operation.build(string.replace(" ", "")).perform()
}

private sealed interface Operation {

    fun perform(): Float

    data class Number(val value: Float): Operation {
        override fun perform(): Float = value
    }

    data class Plus(val left: Operation, val right: Operation): Operation {
        override fun perform(): Float = left.perform() + right.perform()
    }

    data class Minus(val left: Operation, val right: Operation): Operation {
        override fun perform(): Float = left.perform() - right.perform()
    }

    data class Multiply(val left: Operation, val right: Operation): Operation {
        override fun perform(): Float = left.perform() * right.perform()
    }

    data class Divide(val left: Operation, val right: Operation): Operation {
        override fun perform(): Float = left.perform() / right.perform()
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

            return NextFloatResult(expression.substring(0 .. end).toFloat(), end)
        }

        private data class NextFloatResult(val value: Float, val endIndex: Int)
    }
}
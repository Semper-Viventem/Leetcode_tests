package algorithms

import utils.test
import java.math.BigInteger

fun main() = test(
    testData = listOf(
        "1101" to 6,
        "1111011110000011100000110001011011110010111001010111110001" to 85,
        "1111110011101010110011100100101110010100101110111010111110110010" to 89,
        "100100001010010101101000111100100111101111000111111010010001100001000100011001" to 120,
        "10" to 1,
        "1" to 0,
    ),
    testFunctionExecution = ::numSteps,
)

fun numSteps(s: String): Int {
    var result = 0

    var num = BigInteger(s, 2)

    while (num != BigInteger.ONE) {
        if (num % BigInteger.valueOf(2) == BigInteger.ZERO) {
            num /= BigInteger.valueOf(2)
        } else {
            num++
        }
        result++
    }

    return result
}
fun main() {
    val solution = Solution()
    test(
        testData = listOf(
            "42" to 42,
            "-42" to -42,
            "+42" to 42,
            "   +42" to 42,
            "42 some text" to 42,
            "   42 some text" to 42,
            "   -42 some text" to -42,
            "-91283472332" to -2147483648,
            "+-12" to 0,
            "-+12" to 0,
            "2147483648" to 2147483647,
            "2147483647" to 2147483647,
            "-2147483648" to -2147483648,
            "   " to 0,
        ),
        testFunctionExecution = solution::myAtoi
    )
}

class Solution {
    fun myAtoi(s: String): Int {
        if (s.isEmpty()) return 0
        var current = 0

        while (current < s.length && s[current] == ' ') current++

        if (current == s.length) return 0

        var sign = 1
        if (s[current] == '-') {
            sign = -1
            current++
        } else if (s[current] == '+') {
            current++
        }

        val threshold = Int.MAX_VALUE / 10
        var result = 0

        while (current < s.length && s[current] in '0'..'9') {
            val digit = s[current++] - '0'
            if(result > threshold) return if(sign < 0) Int.MIN_VALUE else Int.MAX_VALUE
            if(result == threshold) {
                if(sign < 0 && digit >= 8) return Int.MIN_VALUE
                if(sign > 0 && digit > 7) return Int.MAX_VALUE
            }
            result = result * 10 + digit
        }

        return result * sign
    }
}
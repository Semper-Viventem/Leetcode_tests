import java.lang.IllegalArgumentException
import kotlin.math.exp

fun main() {
    test(
        testData = listOf(
            "t" to true,
            "f" to false,
            "!(f)" to true,
            "|(f,t)" to true,
            "|(f,f)" to false,
            "&(f,t)" to false,
            "&(t,t)" to true,
            "|(&(t,f,t),!(t))" to false,
            "&(|(t,&(t,!(f),|(t,f)),f),!(t),f)" to false
        ),
        testFunctionExecution = ::parseBoolExpr
    )
}

fun parseBoolExpr(expression: String): Boolean {

    return when (expression.first()) {
        't' -> true
        'f' -> false
        '!' -> not(getChildElementsSubstring(expression).first())
        '|' -> or(getChildElementsSubstring(expression))
        '&' -> and(getChildElementsSubstring(expression))
        else -> throw IllegalArgumentException("Unsupported expr: $expression")
    }
}

fun getChildElementsSubstring(string: String): List<Boolean> {
    val subExpressions = mutableListOf<Boolean>()
    var openBrackets = 0
    var startIndex = 2
    for (i in startIndex..string.lastIndex) {
        val currentChar = string[i]

        if ((currentChar == ')' || currentChar == ',') && openBrackets == 0) {
            subExpressions.add(parseBoolExpr(string.substring(startIndex until i)))
            startIndex = i + 1
        } else if (currentChar == ')' && openBrackets > 0) {
            openBrackets--
        } else if (currentChar == '(') {
            openBrackets++
        }
    }

    return subExpressions
}

fun and(exps: List<Boolean>): Boolean {
    for (exp in exps) {
        if (!exp) return false
    }
    return true
}

fun or(exps: List<Boolean>): Boolean {
    for (exp in exps) {
        if (exp) return true
    }
    return false
}

fun not(exp: Boolean) = exp.not()
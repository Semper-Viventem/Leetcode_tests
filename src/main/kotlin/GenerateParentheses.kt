/**
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
 *
 *
 *
 * Example 1:
 *
 * Input: n = 3
 * Output: ["((()))","(()())","(())()","()(())","()()()"]
 *
 * Example 2:
 *
 * Input: n = 1
 * Output: ["()"]
 *
 * https://leetcode.com/problems/generate-parentheses/
 */
fun main() = test(
    testData = listOf(
        3 to listOf("((()))", "(()())", "(())()", "()(())", "()()()"),
    ),
    testFunctionExecution = ::generateParenthesis
)

val parenthesisCache = hashMapOf<Int, List<String>>()
fun generateParenthesis(n: Int): List<String> {
    if (n == 0) return listOf("")
    if (n == 1) return listOf("()")
    if (parenthesisCache[n] != null) return parenthesisCache[n]!!

    val result = mutableListOf<String>()

    permutations(n).map { permutation ->
        var permutationVariants = mutableListOf<List<String>>(mutableListOf())
        permutation.forEachIndexed { permutationNr, case ->
            var variants = generateParenthesis(case - 1)
            var newPermutationVariants = mutableListOf<List<String>>()
            variants.forEach { variant ->
                permutationVariants.forEach { permutationVariant ->
                    newPermutationVariants.add(permutationVariant + listOf("($variant)"))
                }
            }
            permutationVariants = newPermutationVariants
        }
        result += permutationVariants.map { it.joinToString("") }
    }

    parenthesisCache[n] = result

    return result
}

val permutationCache = hashMapOf<Int, List<List<Int>>>()
fun permutations(n: Int): List<List<Int>> {
    if (n == 0) return listOf(emptyList())
    if (n == 1) return listOf(listOf(1))
    if (permutationCache[n] != null) return permutationCache[n]!!
    val result = mutableListOf<List<Int>>()

    for (i in n downTo 1) {
        permutations(n - i).forEach { smallPermutation ->
            val localResult = listOf(i) + smallPermutation
            result.add(localResult)
        }
    }

    permutationCache[n] = result
    return result
}
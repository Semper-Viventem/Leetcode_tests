package algorithms

import utils.test

/**
 * You are given the array paths, where paths[i] = [cityAi, cityBi] means there exists a direct path going from cityAi to cityBi. Return the destination city, that is, the city without any path outgoing to another city.
 *
 * It is guaranteed that the graph of paths forms a line without any loop, therefore, there will be exactly one destination city.
 *
 *
 *
 * Example 1:
 *
 * Input: paths = [["London","New York"],["New York","Lima"],["Lima","Sao Paulo"]]
 * Output: "Sao Paulo"
 * Explanation: Starting at "London" city you will reach "Sao Paulo" city which is the destination city. Your trip consist of: "London" -> "New York" -> "Lima" -> "Sao Paulo".
 *
 * Example 2:
 *
 * Input: paths = [["B","C"],["D","B"],["C","A"]]
 * Output: "A"
 * Explanation: All possible trips are:
 * "D" -> "B" -> "C" -> "A".
 * "B" -> "C" -> "A".
 * "C" -> "A".
 * "A".
 * Clearly the destination city is "A".
 *
 * Example 3:
 *
 * Input: paths = [["A","Z"]]
 * Output: "Z"
 *
 * https://leetcode.com/problems/destination-city/?envType=daily-question&envId=2023-12-15
 *
 */
fun main() = test(
    testData = listOf(
        listOf(listOf("London", "New York"), listOf("New York", "Lima"), listOf("Lima", "Sao Paulo")) to "Sao Paulo",
    ),
    testFunctionExecution = ::destCity
)

fun destCity(paths: List<List<String>>): String {
    val departures = paths.mapTo(hashSetOf()) { it[0] }

    for (path in paths) {
        if (!departures.contains(path[1])) {
            return path[1]
        }
    }

    return ""
}
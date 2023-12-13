import java.util.*

/**
 * You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.
 *
 * Merge all the linked-lists into one sorted linked-list and return it.
 *
 *
 *
 * Example 1:
 *
 * Input: lists = [[1,4,5],[1,3,4],[2,6]]
 * Output: [1,1,2,3,4,4,5,6]
 * Explanation: The linked-lists are:
 * [
 *   1->4->5,
 *   1->3->4,
 *   2->6
 * ]
 * merging them into one sorted list:
 * 1->1->2->3->4->4->5->6
 *
 * Example 2:
 *
 * Input: lists = []
 * Output: []
 *
 * Example 3:
 *
 * Input: lists = [[]]
 * Output: []
 *
 * https://leetcode.com/problems/merge-k-sorted-lists/
 */
fun main() = test(
    testData = listOf(
        arrayOf(
            intArrayOf(1, 4, 5).toListNode(),
            intArrayOf(1, 3, 4).toListNode(),
            intArrayOf(2, 6).toListNode(),
        ) to intArrayOf(1, 1, 2, 3, 4, 4, 5, 6).toListNode(),
        emptyArray<ListNode?>() to intArrayOf().toListNode(),
        arrayOf(intArrayOf().toListNode()) to intArrayOf().toListNode(),
    ),
    testFunctionExecution = ::mergeKLists,
    inputToString = { it.toList().toString() }
)

fun mergeKLists(lists: Array<ListNode?>): ListNode? {
    if (lists.isEmpty()) return null
    val queue = PriorityQueue<ListNode> { first, second -> first!!.`val` - second.`val` }
    lists.forEach {
        it?.let(queue::add)
    }

    val head = ListNode(Int.MIN_VALUE)
    var current = head

    while (queue.isNotEmpty()) {
        val selected = queue.poll()

        current.next = selected
        current = selected
        selected.next?.let(queue::offer)
    }

    return head.next
}


fun mergeKLists1(lists: Array<ListNode?>): ListNode? {
    if (lists.isEmpty()) return null

    var head: ListNode? = null
    var current = head

    var i = 0
    var localMin: ListNode? = lists.getOrNull(0)
    var minIndex = 0
    var localHead: ListNode?

    while (true) {
        localHead = lists[i]

        if (localHead != null && (localMin == null || localHead.`val` < localMin.`val`)) {
            localMin = localHead
            minIndex = i
        }

        if (i == lists.lastIndex) {

            if (localMin == null) {
                break
            }

            if (current == null) {
                head = localMin
                current = head
            } else {
                current.next = localMin
                current = localMin
            }

            lists[minIndex] = localMin.next
            i = 0
            localMin = lists.getOrNull(i)
            minIndex = 0
        } else {
            i++
        }
    }

    return head
}
/**
 * Given a linked list, swap every two adjacent nodes and return its head. You must solve the problem without
 * modifying the values in the list's nodes (i.e., only nodes themselves may be changed.)
 *
 *
 * Example 1:
 *
 * Input: head = [1,2,3,4]
 * Output: [2,1,4,3]
 *
 * Example 2:
 *
 * Input: head = []
 * Output: []
 *
 * Example 3:
 *
 * Input: head = [1]
 * Output: [1]
 *
 * https://leetcode.com/problems/swap-nodes-in-pairs/description/
 */
fun main() = test(
    testData = listOf(
        intArrayOf(1, 2, 3, 4).toListNode() to intArrayOf(2, 1, 4, 3).toListNode(),
        intArrayOf().toListNode() to intArrayOf().toListNode(),
        intArrayOf(1).toListNode() to intArrayOf(1).toListNode(),
    ),
    testFunctionExecution = ::swapPairs,
)

fun swapPairs(head: ListNode?): ListNode? {
    if (head == null) return null
    if (head.next == null) return head

    val resultHead = ListNode(-1).apply { next = head }

    var prev: ListNode? = resultHead
    var current: ListNode? = prev?.next
    var next: ListNode? = current?.next

    while (next != null) {
        prev?.next = next
        current?.next = next?.next
        next?.next = current

        prev = current
        current = prev?.next
        next = current?.next
    }

    return resultHead.next
}
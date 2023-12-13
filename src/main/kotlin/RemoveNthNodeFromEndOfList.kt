/**
 * Given the head of a linked list, remove the nth node from the end of the list and return its head.
 *
 *
 *
 * Example 1:
 *
 * Input: head = [1,2,3,4,5], n = 2
 * Output: [1,2,3,5]
 *
 * Example 2:
 *
 * Input: head = [1], n = 1
 * Output: []
 *
 * Example 3:
 *
 * Input: head = [1,2], n = 1
 * Output: [1]
 *
 * https://leetcode.com/problems/remove-nth-node-from-end-of-list/
 *
 * Definition for singly-linked list.
 * class ListNode(var `val`: Int) {
 *     var next: ListNode? = null
 * }
 */
fun main() = test(
    testData = listOf(
        (intArrayOf(1, 2, 3, 4, 5).toListNode() to 2) to intArrayOf(1, 2, 3, 5).toListNode(),
        (intArrayOf(1).toListNode() to 1) to intArrayOf().toListNode(),
        (intArrayOf(1, 2).toListNode() to 1) to intArrayOf(1).toListNode(),
        (intArrayOf(1, 2).toListNode() to 2) to intArrayOf(2).toListNode(),
    ),
    testFunctionExecution = { (head, n) -> removeNthFromEnd(head, n) },
)

/**
 *
 * 1, 2, 3, 4, 5
 * n = 2
 */
fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
    if (head?.next == null) return null

    var current: ListNode = head
    var target: ListNode = head

    var i = 0 - n

    while (current.next != null) {
        i++
        current = current.next!!
        if (i > 0) {
            target = target.next!!
        }
    }

    if (i == 0) {
        head.next = head.next?.next
    } else if (i < 0) {
        return head.next
    } else {
        target.next = target.next?.next
    }

    return head
}
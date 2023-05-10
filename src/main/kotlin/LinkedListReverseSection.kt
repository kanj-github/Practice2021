// https://leetcode.com/problems/reverse-linked-list-ii
class LinkedListReverseSection {
    fun reverseBetween(head: LLNode?, left: Int, right: Int): LLNode? {

        if (left == right) {
            return head
        }

        head ?: return null

        var p: LLNode? = head
        var count = 1
        var prefix: LLNode? = null

        while(count < left && p != null) {
            prefix = p
            p = p.next
            count++
        }

        val lastInReverse = p
        var previous: LLNode? = null
        var next: LLNode? = p?.next
        while (count <= right && p != null) {
            p.next = previous
            previous = p
            p = next
            next = next?.next
            count++
        }

        lastInReverse?.next = p

        prefix ?: return previous
        prefix.next = previous

        return head
    }
}

fun main() {

    val nums = intArrayOf(1,2)
    val head = LLNode(nums[0])
    var prev = head
    for (i in 1 until nums.size) {
        val n = LLNode(nums[i])
        prev.next = n
        prev = n
    }

    val llr = LinkedListReverseSection()
    val result = llr.reverseBetween(head, 1, 2)
    result?.print()
}

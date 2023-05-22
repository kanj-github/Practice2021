import java.util.PriorityQueue

// https://leetcode.com/problems/merge-k-sorted-lists/description
class LinkedListMergeK {

    fun mergeKLists(lists: Array<LLNode?>): LLNode? {

        val minPq = PriorityQueue<LLNode> { a, b -> a.value - b.value }

        lists.forEach { node ->
            node?.let { minPq.offer(it) }
        }

        var head: LLNode? = null
        var last: LLNode? = null
        while(minPq.isNotEmpty()) {
            val node = minPq.poll()
            node.next?.let {
                minPq.offer(it)
            }
            node.next = null
            if (last == null) {
                head = node
            }
            last?.next = node
            last = node
        }

        return head
    }
}

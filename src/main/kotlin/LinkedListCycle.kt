
class LLNode(var value: Int) {
    var next: LLNode? = null

    fun print() {
        var node: LLNode? = this
        val str = buildString {
            while (node != null) {
                append("${node?.value} -> ")
                node = node?.next
            }

            deleteRange(length - 4, length)
        }
        println(str)
    }
}

class LinkedListCycle {
    fun hasCycle(head: LLNode?): Boolean {

        head ?: return false

        var slow = head.next
        var fast = slow?.next

        slow?.let {
            println("Slow is at ${it.value}")
        } ?: println("Slow null")

        fast?.let {
            println("Fast is at ${it.value}")
        } ?: println("Fast null")

        while (fast != null && slow != null) {

            if (slow == fast) {
                return true
            }

            slow = slow.next
            fast = fast.next?.next

            slow?.let {
                println("Slow is at ${it.value}")
            } ?: println("Slow null")

            fast?.let {
                println("Fast is at ${it.value}")
            } ?: println("Fast null")
        }

        return false
    }
}

fun main() {

    val three = LLNode(3)
    val two = LLNode(2)
    val zero = LLNode(0)
    val four = LLNode(4)
    val one = LLNode(1)
/*
    one.next = two
    two.next = one
    three.next = two
    two.next = zero
    zero.next = four
    four.next = two
*/

    val llc = LinkedListCycle()
    println(llc.hasCycle(one))
}

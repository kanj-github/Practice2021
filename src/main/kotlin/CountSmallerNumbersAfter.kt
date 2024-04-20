class CountSmallerNumbersAfter {

    fun countSmaller(nums: IntArray): List<Int> {
        /*val bst = BST()
        for (i in nums.size - 1 downTo 0) {
            val count = bst.findAndCount(nums[i])
            bst.insert(nums[i])
            nums[i] = count
        }
        return nums.toList() */

        val st = SegmentTree(-10000, 10000)
        for (i in nums.size - 1 downTo 0) {
            val count = st.query(nums[i] - 1)
            st.insert(nums[i])
            nums[i] = count
        }

        return nums.toList()
    }
}

// Frequency tracker
class SegmentTree(private val min: Int, private val max: Int) {

    private val root = Segment(min, max)

    fun insert(n: Int) {

        if (n < min || n > max) {
            return
        }

        var c = root
        c.count++

        while (c.s < c.e) {
            val mid = c.s + (c.e - c.s) / 2
            if (n <= mid) {
                val child = c.l ?: Segment(c.s, mid)
                child.count++
                c.l = child
                c = child
            } else {
                val child = c.r ?: Segment(mid + 1, c.e)
                child.count++
                c.r = child
                c = child
            }
        }
    }

    // Sum of frequencies of numbers from min to n
    fun query(n: Int): Int {

        if (root.count == 0 || n < min || n > max) {
            return 0
        }

        return queryInternal(n, root)
    }

    private fun queryInternal(n: Int, node: Segment): Int {

        if (node.s > n) {
            return 0
        }

        if (node.e <= n) {
            return node.count
        }

        var sum = 0
        node.l?.let {
            sum += queryInternal(n, it)
        }
        node.r?.let {
            sum += queryInternal(n, it)
        }
        return sum
    }

    data class Segment(
        val s: Int,
        val e: Int,
        var count: Int = 0,
        var l: Segment? = null,
        var r: Segment? = null,
    )
}

class BST {

    private var root: Node? = null

    fun findAndCount(n: Int): Int {

        val r = root
        r ?: return 0

        var c = root
        var count = 0

        while (c != null) {
            if (c!!.v < n) {
                count += (c!!.leftTreeSize + 1)
                c = c.r
            } else {
                c = c.l
            }
        }

        return count
    }

    fun insert(n: Int) {

        val r = root

        if (r == null) {
            root = Node(n)
            return
        }

        var p: Node? = null
        var c = root
        while (c != null) {
            if (n < c.v) {
                c.leftTreeSize++
                p = c
                c = c.l
            } else {
                p = c
                c = c.r
            }
        }

        p?.let {
            val node = Node(n)
            if (n < it.v) {
                it.l = node
            } else {
                it.r = node
            }
        }
    }

    data class Node(
        val v: Int,
        var l: Node? = null,
        var r: Node? = null,
        var leftTreeSize: Int = 0,
    )
}

fun main() {
    val solution = CountSmallerNumbersAfter()
    val result = solution.countSmaller(intArrayOf(1, 0, -1))
    println("$result")
}

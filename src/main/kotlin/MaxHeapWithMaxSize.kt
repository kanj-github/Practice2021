// https://leetcode.com/problems/kth-largest-element-in-an-array/description
// Exceeds time limit
// This can be fast only when k is small enough to be treated as a constant
// Quick select works faster on average when k is large
class MaxHeapWithMaxSize(private val maxSize: Int) {

    private val array = arrayListOf<Int>()

    fun insert(n: Int) {

        if (array.size < maxSize) {
            array.add(n)
            for (i in parent(array.size - 1) downTo 0) {
                heapify(i)
            }
            return
        }

        val pos = getIndexOfMin()
        if (n < array[pos]) {
            return
        }

        array[pos] = n
        for (i in parent(pos) downTo 0) {
            heapify(i)
        }
    }

    fun getMinimum(): Int {

        val leafCount = (array.size + 1) / 2

        var min = array[array.size - 1]
        if (leafCount == 1) {
            return min
        }

        for (i in array.size - 2 downTo array.size - leafCount) {
            if (array[i] < min) {
                min = array[i]
            }
        }

        return min
    }

    private fun getIndexOfMin(): Int {

        val leafCount = (array.size + 1) / 2

        var minI = array.size - 1
        var min = array[minI]

        if (leafCount == 1) {
            return minI
        }

        for (i in array.size - 2 downTo array.size - leafCount) {
            if (array[i] < min) {
                min = array[i]
                minI = i
            }
        }

        return minI
    }

    private fun parent(i: Int) = if (i > 0) ((i - 1) / 2) else -1
    private fun left(i: Int) = i * 2 + 1
    private fun right(i: Int) = i * 2 + 2

    fun print() {
        array.forEach {
            print("$it, ")
        }
        println()
    }

    private fun heapify(i: Int) {
        val c = getChildIndexToSwap(i)
        if (c != -1) {
            val temp = array[i]
            array[i] = array[c]
            array[c] = temp
            heapify(c)
        }
    }

    private fun getChildIndexToSwap(i: Int): Int {

        val l = left(i)
        val lC = if (l < array.size) array[l] else null

        val r = right(i)
        val rC = if (r < array.size) array[r] else null

        if (lC != null && lC > array[i] && (rC == null || lC > rC)) {
            return l
        }

        if (rC != null && rC > array[i] && (lC == null || rC > lC)) {
            return r
        }

        return -1
    }
}

fun main() {
    val mh = MaxHeapWithMaxSize(1)
    intArrayOf(3,2,3,1,2,4,5,5,6).forEach {
        mh.insert(it)
        mh.print()
    }
    println(mh.getMinimum())
    //mh.print()
}

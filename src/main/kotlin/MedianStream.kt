
abstract class Heap {

    val array = ArrayList<Int>()

    fun parent(i: Int) = if (i > 0) (i - 1)/2 else -1

    fun left(i: Int) = (i * 2) + 1

    fun right(i : Int) = (i * 2) + 2

    fun heapify(i: Int) {
        val child = getChildIndexToSwap(i)
        if (child != -1) {
            swap(i, child)
            heapify(child)
        }
    }

    abstract fun getChildIndexToSwap(i : Int): Int

    private fun swap(one: Int, other: Int) {
        val temp = array[one]
        array[one] = array[other]
        array[other] = temp
    }

    fun insert(num: Int) {

        array.add(num)

        for (i in parent(array.size - 1) downTo 0) {
            heapify(i)
        }
    }

    fun getTop(): Int {

        val ret = array[0]
        array[0] = array.removeLast()

        heapify(0)
        return ret
    }
}

open class MaxHeap : Heap() {

    override fun getChildIndexToSwap(i: Int): Int {

        val parent = array[i]
        val size = array.size

        val left = left(i)
        val leftChild = if (left < size) array[left] else null

        val right = right(i)
        val rightChild = if (right < size) array[right] else null

        if (leftChild != null && leftChild > parent && (rightChild == null || leftChild > rightChild)) {
            return left
        }

        if (rightChild != null && rightChild > parent && (leftChild == null || rightChild > leftChild)) {
            return right
        }

        return -1
    }
}

class MinHeap : Heap() {

    override fun getChildIndexToSwap(i: Int): Int {

        val parent = array[i]
        val size = array.size

        val left = left(i)
        val leftChild = if (left < size) array[left] else null

        val right = right(i)
        val rightChild = if (right < size) array[right] else null

        if (leftChild != null && leftChild < parent && (rightChild == null || leftChild < rightChild)) {
            return left
        }

        if (rightChild != null && rightChild < parent && (leftChild == null || rightChild < leftChild)) {
            return right
        }

        return -1
    }
}

fun findMedian(arr: Array<Int>): Array<Int> {

    var median  = 0
    val output = ArrayList<Int>()

    val rightHeap = MinHeap()
    val leftHeap = MaxHeap()

    for (num in arr) {
        // Add to a heap
        if (num < median) {
            leftHeap.insert(num)
        } else {
            rightHeap.insert(num)
        }

        // Balance heap
        var sizeDifference = leftHeap.array.size - rightHeap.array.size
        if (sizeDifference == 2) {
            // Left side has 2 more items. Move 1 to right
            val leftTop = leftHeap.getTop()
            rightHeap.insert(leftTop)
        } else if (sizeDifference == -2) {
            // Right side has 2 more items. Move 1 to left
            val rightTop = rightHeap.getTop()
            leftHeap.insert(rightTop)
        }

        // Calculate median
        sizeDifference = leftHeap.array.size - rightHeap.array.size
        if (sizeDifference == 0) {
            median = (leftHeap.array[0] + rightHeap.array[0]) / 2
        } else if (sizeDifference == 1) {
            median = leftHeap.array[0]
        } else if (sizeDifference == -1) {
            median = rightHeap.array[0]
        }

        output.add(median)
    }

    return output.toTypedArray()
}

fun main(args : Array<String>) {

    var output = findMedian(arrayOf(5, 15, 1, 3))
    output.forEach { print("$it ") }
    println()

    output = findMedian(arrayOf(2, 4, 7, 1, 5, 3))
    output.forEach { print("$it ") }
    println()
}

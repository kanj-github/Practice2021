
class RemoveSecondMaxHeap: MaxHeap() {

    fun removeSecond(): Int? {

        if (array.size < 3) {
            return null
        }

        val index = if (array[1] > array[2]) 1 else 2
        val second = array[index]
        array[0] += second

        if (array.size > 3) {
            array[index] = array.removeLast()
            heapify(index)
        } else {
            if (index == 1) {
                array[1] = array.removeLast()
            } else if (index == 2) {
                 array.removeLast()
            }
        }

        return array[0]
    }
}

fun getTotalTime(arr: Array<Int>): Int {

    if (arr.size < 2) {
        return arr.sumOf { it }
    }

    val heap = RemoveSecondMaxHeap()
    arr.forEach { heap.insert(it) }

    var time = 0
    while (true) {
        heap.removeSecond()?.let {
            time += it
        } ?: break
    }

    time += (heap.array[0] + heap.array[1])

    return time
}

fun main(args : Array<String>) {

    var output = getTotalTime(arrayOf(4, 2, 1, 3))
    println(output)

    output = getTotalTime(arrayOf(2, 3, 9, 8, 4))
    println(output)
}

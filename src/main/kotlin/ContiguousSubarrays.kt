// This passed the tests in Java despite being N square

fun countSubarraysNSquare(args: Array<Int>): Array<Int> {
    val output = mutableListOf<Int>()
    args.forEachIndexed { index, _ ->
        output.add(getCount(index, args))
    }
    return output.toTypedArray()
}

fun getCount(index: Int, array: Array<Int>): Int {

    var count = 0

    // Ends
    for (i in index downTo 0) {
        if (array[i] <= array[index]) {
            count += 1
        } else {
            break
        }
    }

    // Starts
    for (i in index until array.size) {
        if (array[i] <= array[index]) {
            count += 1
        } else {
            break
        }
    }

    return count - 1
}

class Stack {

    private val items = ArrayList<Int>()

    fun push(item: Int) {
        items.add(item)
    }

    fun pop(): Int {
        return items.removeLast()
    }

    fun top(): Int {
        return items.last()
    }

    fun isEmpty() = items.isEmpty()

    fun clear() = items.clear()
}

fun countSubarraysN(args: Array<Int>): Array<Int> {
    val starts = mutableListOf<Int>()
    val ends = mutableListOf<Int>()

    ends.add(1)

    val maxStack = Stack()
    maxStack.push(0)

    for (i in 1 until args.size) {

        val num = args[i]
        while (!maxStack.isEmpty() && args[maxStack.top()] <= num) {
            maxStack.pop()
        }

        if (maxStack.isEmpty()) {
            ends.add(i + 1)
        } else {
            ends.add(i - maxStack.top())
        }

        maxStack.push(i)
    }

    maxStack.clear()

    starts.add(1)
    maxStack.push(args.size - 1)

    for (i in args.size - 2 downTo 0) {

        val num = args[i]
        while (!maxStack.isEmpty() && args[maxStack.top()] <= num) {
            maxStack.pop()
        }

        if (maxStack.isEmpty()) {
            starts.add(0, args.size - i)
        } else {
            starts.add(0, maxStack.top() - i)
        }

        maxStack.push(i)
    }

    val output = ArrayList<Int>()
    args.forEachIndexed {index, _ ->
        output.add(starts[index] + ends[index] - 1)
    }

    return output.toTypedArray()
}

fun main(args: Array<String>) {
    val input = arrayOf(3, 4, 1, 6, 2)
    var output = countSubarraysNSquare(input)
    output.forEach { print("$it ") }
    println()
    output = countSubarraysN(input)
    output.forEach { print("$it ") }
    println()
}

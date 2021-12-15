import java.lang.Integer.min

fun findMinArray(arr: Array<Int>, k: Int): Array<Int> {

    var swapBalance = k
    var i = 0
    val arrayList = arr.toMutableList()

    while (swapBalance > 0 && i < arr.size - 1) {

        var smallest = arr[i]
        var smallestIndex = i

        for (j in i + 1 until min(arr.size, i + k + 1)) {
            if (arr[j] < smallest) {
                smallest = arr[j]
                smallestIndex = j
            }
        }

        if (smallestIndex > i) {
            swapBalance -= (smallestIndex - i)
            arrayList.removeAt(smallestIndex)
            arrayList.add(0, smallest)
        }

        i++
    }

    return arrayList.toTypedArray()
}

fun main(args : Array<String>) {
    var output = findMinArray(arrayOf(5, 3, 1), 2)
    output.forEach { print("$it ") }
    println()

    output = findMinArray(arrayOf(8, 9, 11, 2, 1), 3)
    output.forEach { print("$it ") }
    println()
}

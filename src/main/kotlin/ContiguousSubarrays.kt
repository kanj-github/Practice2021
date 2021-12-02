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

/*
fun countSubarraysN(args: Array<Int>): Array<Int> {
    val starts = mutableListOf<Int>()
    val ends = mutableListOf<Int>()

    var maxTillNow = args[0]
    var maxIndex = 0
    ends.add(1)
    for (i in 1 until args.size) {

        if (args[i] < args[i - 1]) {
            ends.add(1)
            continue
        }

        if (args[i] == args[i - 1]) {
            ends.add(ends.last() + 1)
            continue
        }


    }

}
*/

fun main(args: Array<String>) {
    val input = arrayOf(3, 4, 1, 6, 2)
    val output = countSubarraysNSquare(input)
    output.forEach { println(it) }
}

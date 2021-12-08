
fun numberOfWays(arr: Array<Int>, k: Int): Int {

    arr.sort()
    var counter = 0

    arr.forEachIndexed { index, num ->
        counter += getCount(index, arr, k)
    }

    return counter
}

fun getCount(index: Int, arr: Array<Int>, sum: Int): Int {
    var counter = 0
    for (i in index + 1 until arr.size) {
        val two = arr[index] + arr[i]
        if (two == sum) {
            counter++
        } else if (two > sum) {
            break
        }
    }
    return counter
}

fun numberOfWaysFaster(arr: Array<Int>, k: Int): Int {

    quickSort(arr)

    var i = 0
    var j = arr.size - 1
    var counter = 0

    while (true) {

        val sum = arr[i] + arr[j]

        if (sum == k) {
            counter += 1
            i++
        } else if (sum < k) {
            i++
        } else {
            j--
        }

        if (i <= j) {
            break
        }
    }

    return counter
}

fun main(args : Array<String>) {
    var output = numberOfWaysFaster(arrayOf(1, 2, 3, 4, 3), 6)
    assert(output == 2)
    output = numberOfWaysFaster(arrayOf(1, 5, 3, 3, 3), 6)
    assert(output == 4)
}

fun quickSort(arr: Array<Int>) {
    sort(arr, 0, arr.size - 1)
}

fun sort(arr: Array<Int>, start: Int, end: Int) {

    if (start >= end) {
        return
    }

    val partition = partition(arr, start, end)
    sort(arr, 0, partition - 1)
    sort(arr, partition + 1, end)
}

fun partition(arr: Array<Int>, start: Int, end: Int): Int {

    val pivot = arr[end]
    var pi = start - 1

    for (i in start until end) {
        if (arr[i] < pivot) {
            pi += 1
            swap(arr, pi, i)
        }
    }

    swap(arr, pi + 1, end)
    return pi + 1
}

fun swap(arr: Array<Int>, one: Int, two: Int) {

    if (one == two) {
        return
    }

    val temp = arr[one]
    arr[one] = arr[two]
    arr[two] = temp
}

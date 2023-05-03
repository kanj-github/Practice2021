// https://leetcode.com/problems/insert-interval/description/
class InsertInterval {

    fun insert(intervals: Array<IntArray>, newInterval: IntArray): Array<IntArray> {

        val output = arrayListOf<IntArray>()

        var i = 0
        while (i < intervals.size && compare(intervals[i], newInterval) == -1) {
            output.add(intervals[i++])
        }

        if (i == intervals.size) {
            output.add(newInterval)
            return output.toTypedArray()
        }

        if (compare(intervals[i], newInterval) == 0) {
            var last = join(intervals[i++], newInterval)
            while (i < intervals.size && compare(intervals[i], last) == 0) {
                last = join(last, intervals[i++])
            }

            output.add(last)
        } else {
            output.add(newInterval)
        }

        while (i < intervals.size) {
            output.add(intervals[i++])
        }

        return output.toTypedArray()
    }

    private fun compare(a: IntArray, b: IntArray): Int {

        return if (a[1] < b[0]) {
            // B starts after A
            -1
        } else if (a[0] > b[1]) {
            // A starts after B
            1
        } else {
            0
        }
    }

    private fun join(a: IntArray, b: IntArray): IntArray {
        return intArrayOf(minOf(a[0], b[0]), maxOf(a[1], b[1]))
    }
}

fun main() {
    val ii = InsertInterval()
    val intervals: Array<IntArray> = arrayOf(
        intArrayOf(1, 2),
        intArrayOf(3, 5),
        intArrayOf(6, 7),
        intArrayOf(8, 10),
        intArrayOf(12, 16),
    )
    val result = ii.insert(intervals, intArrayOf(4, 8))
    result.forEach {
        println("${it[0]} - ${it[1]}")
    }
}

// https://leetcode.com/problems/merge-intervals/description/
class MergeIntervals {

    fun merge(intervals: Array<IntArray>): Array<IntArray> {

        if (intervals.size < 2) {
            return intervals
        }

        val sorted = intervals.sortedWith(compareBy({ it[0] }, { it[1] }))
        val merged = arrayListOf<IntArray>()

        var merging: IntArray? = null
        for (i in sorted) {

            val curr = merging
            if (curr == null) {
                merging = i
                continue
            }

            if (overlaps(curr, i)) {
                merging = join(curr, i)
            } else {
                merged.add(curr)
                merging = i
            }
        }

        merging?.let {
            merged.add(it)
        }

        return merged.toTypedArray()
    }

    private fun overlaps(a: IntArray, b: IntArray): Boolean {

        val compare = if (a[1] < b[0]) {
            // B starts after A
            -1
        } else if (a[0] > b[1]) {
            // A starts after B
            1
        } else {
            0
        }

        return compare == 0
    }

    private fun join(a: IntArray, b: IntArray): IntArray {
        return intArrayOf(minOf(a[0], b[0]), maxOf(a[1], b[1]))
    }
}

fun main() {

    val mi = MergeIntervals()

    // [[1,3],[2,6],[8,10],[15,18]]
    val intervals = arrayOf(
        intArrayOf(1, 4),
        intArrayOf(4, 5),
        //intArrayOf(8, 10),
        //intArrayOf(15, 18),
        //intArrayOf(3, 6),
    )
    val merged = mi.merge(intervals)
    merged.forEach {
        println("${it[0]} - ${it[1]}")
    }
}

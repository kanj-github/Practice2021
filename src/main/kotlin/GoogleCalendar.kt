// Find the number of meeting rooms
class GoogleCalendar {

    fun calculate(intervals: Array<IntArray>): IntArray {
        val merged = merge(intervals)
        return merged.map { getRoomsTwoPointer(it) }.toIntArray()
    }

    private fun getRooms(intervals: Array<IntArray>): Int {

        if (intervals.size == 1) {
            return 1
        }

        // rooms[i] = time till when room i is occupied
        val rooms = arrayListOf(intervals[0][1])
        println("${intervals[0][0]} - ${intervals[0][1]} in room 0")

        for (i in 1 until intervals.size) {
            var roomI = -1
            // Find the first room that can accommodate this interval
            for (j in 0 until rooms.size) {
                if (intervals[i][0] >= rooms[j]) {
                    roomI = j
                    break
                }
            }

            if (roomI == -1) {
                // Add a room
                rooms.add(intervals[i][1])
                println("${intervals[i][0]} - ${intervals[i][1]} in room ${rooms.size - 1}")
            } else {
                rooms[roomI] = intervals[i][1]
                println("${intervals[i][0]} - ${intervals[i][1]} in room $roomI")
            }
        }

        return rooms.size
    }

    // https://www.youtube.com/watch?v=FdzJmTCVyJU
    private fun getRoomsTwoPointer(intervals: Array<IntArray>): Int {

        if (intervals.size == 1) {
            return 1
        }

        val starts = intervals.map { it[0] }.sorted()
        val ends = intervals.map { it[1] }.sorted()

        var startI = 0
        var endI = 0
        var maxCount = 0
        var count = 0
        while (startI < starts.size) {
            if (starts[startI] < ends[endI]) {
                count++
                maxCount = maxOf(maxCount, count)
                startI++
            } else if (starts[startI] >= ends[endI]) {
                count--
                endI++
            }
        }

        return maxCount
    }

    private fun merge(intervals: Array<IntArray>): Array<Array<IntArray>> {

        val sorted = intervals.sortedWith(compareBy({ it[0] }, { it[1] }))

        val result = ArrayList<Array<IntArray>>()
        var intervalMerge: IntervalMerge? = null

        for (i in sorted) {
            val curr = intervalMerge
            if (curr == null) {
                intervalMerge = IntervalMerge(i)
                continue
            }

            if (curr.merges(i)) {
                curr.merge(i)
            } else {
                result.add(curr.group.toTypedArray())
                intervalMerge = IntervalMerge(i)
            }
        }

        intervalMerge?.let {
            result.add(it.group.toTypedArray())
        }

        return result.toTypedArray()
    }

    private class IntervalMerge(
        private val interval: IntArray
    ) {
        val group = arrayListOf(IntArray(interval.size) { interval[it] })

        fun merge(i: IntArray) {
            interval[0] = minOf(i[0], interval[0])
            interval[1] = maxOf(i[1], interval[1])
            group.add(i)
        }

        fun merges(i: IntArray): Boolean {

            val compare = if (interval[1] <= i[0]) {
                -1
            } else if (interval[0] >= i[1]) {
                1
            } else {
                0
            }

            return compare == 0
        }
    }
}

fun main() {

    // 1-8, 8-10, 1-3, 3-6, 4-5, 5,9
    // 1-3, 1-8, 3-6, 4-5, 5-9, 8-10
    // room0 - 1-3, 3-6, 8-10
    // room1 - 1-8
    // room2 - 4-5, 5-9

    // 1-3, 4-5, 3-6, 1-8, 5-9, 8-10
    // room0 - 1-3, 4-5, 5-9
    // room1 - 3-6, 8-10
    // room2 - 1-8
    val intervals: Array<IntArray> = arrayOf(
        intArrayOf(1, 3),
        intArrayOf(2, 3),
        intArrayOf(3, 4),
        intArrayOf(11, 18),
        intArrayOf(18, 20),
        intArrayOf(11, 13),
        intArrayOf(13, 16),
        intArrayOf(14, 15),
        intArrayOf(15, 19),
    )

    val gc = GoogleCalendar()
    val result = gc.calculate(intervals)
    result.forEach {
        println(it)
    }
}

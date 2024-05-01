class LISWithIncreasingDifference {

    fun getMaxLengthDp(array: IntArray): Int {

        var opCount = 0

        val DP = Array(array.size) { arrayListOf<Pair<Int, Int>>() } // Pair has diff & max len.
        // DP[i] contains max len of all sub sequences ending at i.

        DP[0].add(Pair(0, 1))

        var max = 1
        for (i in 1 until array.size) {
            for (j in 0 until i) {
                if (array[j] >= array[i]) {
                    continue
                }

                val diff = array[i] - array[j]
                DP[j].filter { it.first < diff }.forEach {
                    max = maxOf(max, it.second + 1)
                    DP[i].add(Pair(diff, it.second + 1))
                    opCount++
                }
            }

            DP[i].add(Pair(0, 1))
        }

        println("getMaxLengthDp did $opCount ops")
        return max
    }

    fun getMaxLength(array: IntArray): Int {

        if (array.isEmpty()) {
            return 0
        }
        var opCount = 0
        var max = 1
        val choices = arrayListOf(Choice(array[0], 1, 0))
        for (i in 1 until array.size) {
            val n = array[i]
            val choiceCount = choices.size
            for (j in 0 until choiceCount) {
                val c = choices[j]
                val diff = n - c.last
                if (diff < 1) {
                    break // Because last increases in choices
                }
                if (diff > c.lastDiff) {
                    max = maxOf(max, c.length + 1)
                    choices.add(Choice(n, c.length + 1, diff))
                    opCount++
                }
            }
            choices.add(Choice(n, 1, 0))
        }

        println("getMaxLength did $opCount ops")
        return max
    }

    data class Choice(
        val last: Int,
        val length: Int,
        val lastDiff: Int,
    )
}

fun main() {
    val sol = LISWithIncreasingDifference()
    println(sol.getMaxLengthDp(intArrayOf(1,2,3,4,5,6)))
    println(sol.getMaxLengthDp(intArrayOf(1,2,90,91,93,96)))
    println(sol.getMaxLength(intArrayOf(1,2,3,4,5,6)))
    println(sol.getMaxLength(intArrayOf(1,2,90,91,93,96)))
}

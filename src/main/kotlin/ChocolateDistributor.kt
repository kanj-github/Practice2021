// https://leetcode.com/discuss/interview-question/350800/Google-or-Onsite-or-Chocolate-Sweetness
// Similar to Skienna P308
class ChocolateDistributor(
    private val A: IntArray, // Sweetness
    private val k: Int, // k is the number of cuts. Number of partitions = k + 1
) {

    fun solve(): Int {
        val P = IntArray(A.size) { A[it] }
        for (i in 1 until A.size) {
            P[i] += P[i-1]
        }

        val memo = Array (A.size) { IntArray(k) { -1 } }
        return getMaxMinToTheRight(0, k, P, memo)
    }

    fun solve2(): Int {

        val P = IntArray(A.size) { A[it] }
        for (i in 1 until A.size) {
            P[i] += P[i-1]
        }

        val DP = Array (A.size) { IntArray(k + 1) { Int.MIN_VALUE } }
        val splits = Array (A.size) { IntArray(k + 1) { -1 } }

        for (i in 0 until A.size) {
            DP[i][0] = P[i]
        }

        for (i in 1 until A.size) {
            for (j in 1..k) {
                var max = Int.MIN_VALUE
                for (x in 0 until i) {
                    val min = minOf(sumOfRange(x + 1, i, P), DP[x][j - 1])
                    if (min > max) {
                        max = min
                        splits[i][j] = x
                    }
                }
                DP[i][j] = max
            }
        }

        var cutsLeft = k
        var partitionEnd = A.size - 1
        val partitions = Array(k + 1) { arrayListOf<Int>() }
        while (cutsLeft >= 0) {
            val partitionStart = splits[partitionEnd][cutsLeft] + 1
            for (i in partitionStart..partitionEnd) {
                partitions[cutsLeft].add(A[i])
            }
            cutsLeft--
            partitionEnd = partitionStart - 1
        }
        for (row in partitions) {
            println("$row")
        }

        return DP.last().last()
    }

    private fun getMaxMinToTheRight(start: Int, k: Int, P: IntArray, memo: Array<IntArray>): Int {

        if (k == 0) {
            return sumOfRange(start, A.size - 1, P)
        }

        if (memo[start][k - 1] != -1) {
            return memo[start][k - 1]
        }

        var maxChoice = Int.MIN_VALUE
        for (j in start until A.size - k) {
            val sumLeft = sumOfRange(start, j, P)
            val minRight = getMaxMinToTheRight(j + 1, k - 1, P, memo)
            val minChoice = minOf(sumLeft, minRight)

            maxChoice = maxOf(maxChoice, minChoice)
        }

        memo[start][k - 1] = maxChoice

        return maxChoice
    }

    private fun sumOfRange(s: Int, e: Int, P: IntArray): Int {
        return when (s) {
            e -> A[s]
            0 -> P[e]
            else -> P[e] - P[s - 1]
        }
    }
}

fun main() {
    val solver = ChocolateDistributor(intArrayOf(6, 3, 2, 8, 7, 5), 3)
    val result = solver.solve2()
    println(result)
}

import kotlin.math.abs

// https://leetcode.com/problems/minimize-the-difference-between-target-and-chosen-elements/

// Memoize using matrix DP[rows][max possible sum]
// DP[i][j] stores the minimum difference between target & (sum of item selected from all rows)
// when the sum of item selected from rows 0..i is j.
const val MAX_POSSIBLE_SUM = 4900

fun minimizeTheDifference(mat: Array<IntArray>, target: Int): Int {

    if (mat.isEmpty()) {
        return target
    }

    val dp = arrayListOf<IntArray>().apply {
        repeat(mat.size) {
            add(IntArray(MAX_POSSIBLE_SUM) { -1 })
        }
    }.toTypedArray()

    return recursive(mat, dp, target, 0, 0)
}

fun recursive(mat: Array<IntArray>, dp: Array<IntArray>, target: Int, rowIndex: Int, sumFromPreviousRows: Int): Int {

    if (rowIndex == mat.size) {
        return abs(sumFromPreviousRows - target)
    }

    dp[rowIndex][sumFromPreviousRows].let {
        if (it != -1) {
            return it
        }
    }

    var minDiff = Int.MAX_VALUE
    for (i in mat[rowIndex]) {
        minDiff = minOf(minDiff, recursive(mat, dp, target, rowIndex + 1, sumFromPreviousRows + i))
    }
    dp[rowIndex][sumFromPreviousRows] = minDiff

    return minDiff
}

fun main() {

    /*val matrix = arrayListOf<IntArray>().apply {
        add(arrayOf(1, 2, 3).toIntArray())
        add(arrayOf(4, 5, 6).toIntArray())
        add(arrayOf(7, 8, 9).toIntArray())
    }.toTypedArray()*/

    val matrix = arrayListOf<IntArray>().apply {
        add(arrayOf(1, 2, 9, 8, 7).toIntArray())
    }.toTypedArray()

    println(minimizeTheDifference(matrix, 6))
}

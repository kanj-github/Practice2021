// https://leetcode.com/problems/rotate-image/description
class MatrixInPlace {

    fun reflectTopLeftToBottomRight(matrix: Array<IntArray>) {

        val n = matrix.size
        if (n < 2) {
            return
        }

        for (i in 0 until n - 1) { // From 0 to n - 2
            for (j in 0 until n - i - 1) { // From 0 to n - i - 2
                swap(matrix, i, j, n - j - 1, n - i - 1)
            }
        }
    }

    fun reverseRows(matrix: Array<IntArray>) {
        val n = matrix.size
        matrix.forEach {  row ->
            var i = 0
            var j = n - 1
            while (j > i) {
                swap(row, i++, j--)
            }
        }
    }

    private fun swap(a: IntArray, x: Int, y: Int) {
        val temp = a[x]
        a[x] = a[y]
        a[y] = temp
    }

    private fun swap(m: Array<IntArray>, i: Int, j: Int, x: Int, y: Int) {
        val temp = m[i][j]
        m[i][j] = m[x][y]
        m[x][y] = temp
    }
}

fun main() {
    val mip = MatrixInPlace()
    /*val matrix = arrayOf(
        intArrayOf(1, 2, 3),
        intArrayOf(4, 5, 6),
        intArrayOf(7, 8, 9),
    )*/
    /*val matrix = arrayOf(
        intArrayOf(1, 2),
        intArrayOf(3, 4),
    )*/
    val matrix = arrayOf(
        intArrayOf(1, 2, 3, 4),
        intArrayOf(5, 6, 7, 8),
        intArrayOf(9, 10, 11, 12),
        intArrayOf(13, 14, 15, 16),
    )
    mip.reverseRows(matrix)
    mip.reflectTopLeftToBottomRight(matrix)
    matrix.forEach { row ->
        row.forEach {
            print("$it ")
        }
        println()
    }
}

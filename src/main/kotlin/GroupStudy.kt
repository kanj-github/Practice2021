// Blog- https://codeforces.com/blog/ecnerwala
// Chinese solution- https://github.com/JunBinLiang/Codeforce/blob/main/code/626F.txt

class GroupStudy(
    private val A: IntArray,
    private val maxK: Int,
) {

    private lateinit var DP: Array<Array<Array<Long>>>

    fun solve(): Long {

        DP = Array(A.size) { Array(A.size) { Array(maxK + 1) { -1L } } }

        val res = dfs(0, 0, 0)

        for (k in 0 until maxK + 1) {
            println("DP for k = $k")
            DP.forEach { x ->
                println("${x.map { it[k] }}")
            }
        }

        return res
    }

    private fun dfs(i: Int, j: Int, k: Int, indent: ArrayList<Char> = arrayListOf()): Long {

        val id = "${String(indent.toCharArray())}dfs - $i, $j, $k"
        println("$id start")

        if (k > maxK || j >= DP.size) {
            println("$id end k or j exceeded")
            return 0L
        }

        if (i >= A.size) {
            println("$id end OOB")
            return if (j == 0) 1L else 0L
        }

        if (DP[i][j][k] != -1L) {
            println("$id end DP")
            return DP[i][j][k]
        }

        var res = 0L
        indent.add('\t')
        if (j == 0) {
            res += dfs(i + 1, 1, k, indent) //open
            res += dfs(i + 1, 0, k, indent) //close on itself
        } else {
            val cost = (A[i] - A[i - 1]) * j
            //open
            res += dfs(i + 1, j + 1, k + cost, indent)
            //close
            res += j.toLong() * dfs(i + 1, j - 1, k + cost, indent)
            //regular
            res += (j + 1).toLong() * dfs(i + 1, j, k + cost, indent)
        }
        indent.removeLast()

        DP[i][j][k] = res
        println("$id end res = $res")
        return res
    }
}

fun main() {
    val gs = GroupStudy(intArrayOf(23, 24), 1)
    val res = gs.solve()
    println("Result is $res")
}

/*

Input: [23, 24], k = 1

dfs - 0, 0, 0 start
	dfs - 1, 1, 0 start
		dfs - 2, 2, 1 start
		dfs - 2, 2, 1 end k or j exceeded
		dfs - 2, 0, 1 start
		dfs - 2, 0, 1 end OOB
		dfs - 2, 1, 1 start
		dfs - 2, 1, 1 end OOB
	dfs - 1, 1, 0 end res = 1
	dfs - 1, 0, 0 start
		dfs - 2, 1, 0 start
		dfs - 2, 1, 0 end OOB
		dfs - 2, 0, 0 start
		dfs - 2, 0, 0 end OOB
	dfs - 1, 0, 0 end res = 1
dfs - 0, 0, 0 end res = 2
Result is 2
DP for k = 0
[2, -1]
[1, 1]
DP for k = 1
[-1, -1]
[-1, -1]

*/

/*

Input [23, 24, 25], k = 1

dfs - 0, 0, 0 start
	dfs - 1, 1, 0 start
		dfs - 2, 2, 1 start
			dfs - 3, 3, 3 start
			dfs - 3, 3, 3 end k or j exceeded
			dfs - 3, 1, 3 start
			dfs - 3, 1, 3 end k or j exceeded
			dfs - 3, 2, 3 start
			dfs - 3, 2, 3 end k or j exceeded
		dfs - 2, 2, 1 end res = 0
		dfs - 2, 0, 1 start
			dfs - 3, 1, 1 start
			dfs - 3, 1, 1 end OOB
			dfs - 3, 0, 1 start
			dfs - 3, 0, 1 end OOB
		dfs - 2, 0, 1 end res = 1
		dfs - 2, 1, 1 start
			dfs - 3, 2, 2 start
			dfs - 3, 2, 2 end k or j exceeded
			dfs - 3, 0, 2 start
			dfs - 3, 0, 2 end k or j exceeded
			dfs - 3, 1, 2 start
			dfs - 3, 1, 2 end k or j exceeded
		dfs - 2, 1, 1 end res = 0
	dfs - 1, 1, 0 end res = 1
	dfs - 1, 0, 0 start
		dfs - 2, 1, 0 start
			dfs - 3, 2, 1 start
			dfs - 3, 2, 1 end OOB
			dfs - 3, 0, 1 start
			dfs - 3, 0, 1 end OOB
			dfs - 3, 1, 1 start
			dfs - 3, 1, 1 end OOB
		dfs - 2, 1, 0 end res = 1
		dfs - 2, 0, 0 start
			dfs - 3, 1, 0 start
			dfs - 3, 1, 0 end OOB
			dfs - 3, 0, 0 start
			dfs - 3, 0, 0 end OOB
		dfs - 2, 0, 0 end res = 1
	dfs - 1, 0, 0 end res = 2
dfs - 0, 0, 0 end res = 3
Result is 3
DP for k = 0
[3, -1, -1]
[2, 1, -1]
[1, 1, -1]
DP for k = 1
[-1, -1, -1]
[-1, -1, -1]
[1, 0, 0]

*/

/*

Input [23, 24, 25], k = 2

dfs - 0, 0, 0 start
	dfs - 1, 1, 0 start
		dfs - 2, 2, 1 start
			dfs - 3, 3, 3 start
			dfs - 3, 3, 3 end k or j exceeded
			dfs - 3, 1, 3 start
			dfs - 3, 1, 3 end k or j exceeded
			dfs - 3, 2, 3 start
			dfs - 3, 2, 3 end k or j exceeded
		dfs - 2, 2, 1 end res = 0
		dfs - 2, 0, 1 start
			dfs - 3, 1, 1 start
			dfs - 3, 1, 1 end OOB
			dfs - 3, 0, 1 start
			dfs - 3, 0, 1 end OOB
		dfs - 2, 0, 1 end res = 1
		dfs - 2, 1, 1 start
			dfs - 3, 2, 2 start
			dfs - 3, 2, 2 end OOB
			dfs - 3, 0, 2 start
			dfs - 3, 0, 2 end OOB
			dfs - 3, 1, 2 start
			dfs - 3, 1, 2 end OOB
		dfs - 2, 1, 1 end res = 1
	dfs - 1, 1, 0 end res = 3
	dfs - 1, 0, 0 start
		dfs - 2, 1, 0 start
			dfs - 3, 2, 1 start
			dfs - 3, 2, 1 end OOB
			dfs - 3, 0, 1 start
			dfs - 3, 0, 1 end OOB
			dfs - 3, 1, 1 start
			dfs - 3, 1, 1 end OOB
		dfs - 2, 1, 0 end res = 1
		dfs - 2, 0, 0 start
			dfs - 3, 1, 0 start
			dfs - 3, 1, 0 end OOB
			dfs - 3, 0, 0 start
			dfs - 3, 0, 0 end OOB
		dfs - 2, 0, 0 end res = 1
	dfs - 1, 0, 0 end res = 2
dfs - 0, 0, 0 end res = 5
Result is 5
DP for k = 0
[5, -1, -1]
[2, 3, -1]
[1, 1, -1]
DP for k = 1
[-1, -1, -1]
[-1, -1, -1]
[1, 1, 0]
DP for k = 2
[-1, -1, -1]
[-1, -1, -1]
[-1, -1, -1]

*/

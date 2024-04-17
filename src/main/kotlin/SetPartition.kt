/**
 * Naive Set Partition
 * */
class SetPartition(
    private val A: IntArray,
) {

    private var count = 0L

    private val V = BooleanArray(A.size) { false }
    private val sg = SelectionsGenerator(A, V)

    fun partition(subsets: ArrayList<List<Int>>, remaining: Int, after: Int) {

        if (remaining == 0) {
            //printSubsets(subsets)
            count++
            return
        }

        if (after == A.size) {
            return
        }

        for (i in 0 until remaining) {
            //println("Select ${i + 1} from $after")
            val selection = sg.select(i + 1, after)
            //println("Selections:")
            //printSubsets(selection)
            for (s in selection) {
                s.forEach { V[it] = true }
                subsets.add(s)
                partition(subsets, remaining - i - 1, s[0] + 1)
                s.forEach { V[it] = false }
                subsets.removeLast()
            }
        }
    }

    fun printStats() {
        //sg.printStats()
        println("$count ways")
    }

    fun printSubsets(subsets: List<List<Int>>) {
        val str = buildString {
            subsets.forEach { indices ->
                val mapped = indices.map { A[it] }
                append("$mapped")
            }
            append(", ")
        }
        if (str.endsWith(", ")) {
            println(str.substring(0, str.length - 2))
        } else {
            println("No subsets")
        }
    }
}

class SelectionsGenerator(
    private val A: IntArray,
    private val V: BooleanArray,
) {

    private var total = 0
    private var memoized = 0

    // Memoization didn't work because selections also depend on V
    private val memo = mutableMapOf<SelectionsKey, List<List<Int>>>()
    private val path = arrayListOf<Int>()

    fun select(size: Int, from: Int): List<List<Int>> {

        total++

        val key = SelectionsKey(from, size)
/*
        val precalculated = memo[key]
        if (precalculated != null) {
            memoized++
            return precalculated
        }
*/

        path.clear()

        val results = arrayListOf<List<Int>>()
        selectInternal(size, from, results)
        memo[key] = ArrayList(results)
        return results
    }

    private fun selectInternal(size: Int, from: Int, results: ArrayList<List<Int>>) {

        if (size > A.size) {
            return
        }

        if (size == 0) {
            results.add(ArrayList(path))
            return
        }

        if (from == A.size) {
            return
        }

        for (i in from until A.size) {
            if (!V[i]) {
                path.add(i)
                V[i] = true
                selectInternal(size - 1, i + 1, results)
                V[i] = false
                path.removeLast()
            }
        }
    }

    fun printStats() {
        println("$memoized memoized out of $total")
    }
}

data class SelectionsKey(
    val from: Int,
    val size: Int,
)

fun selections2(size: Int, after: Int, path: ArrayList<Int>,
                V: BooleanArray, A: IntArray, results: ArrayList<List<Int>>,
                min: Int, max: Int, k: Int) {

    if (size > A.size) {
        return
    }

    if (size == 0) {
        results.add(ArrayList(path))
        return
    }

    if (after == A.size) {
        return
    }

    for (i in after until A.size) {
        if (!V[i]) {

            val mMin = minOf(min, A[i])
            val mMax = maxOf(max, A[i])
            if (mMax - mMin > k) {
                continue
            }

            path.add(i)
            V[i] = true
            selections2(size - 1, i + 1, path, V, A, results, mMin, mMax, k)
            V[i] = false
            path.removeLast()
        }
    }
}


fun main() {
    val ar = IntArray(20) { it + 1 }
    val sp = SetPartition(ar)
    val result = arrayListOf<List<Int>>()
    sp.partition(result, ar.size, 0)
    sp.printStats()
}
// 5 - 52
// 10 - 115975
// 20 - Took too long
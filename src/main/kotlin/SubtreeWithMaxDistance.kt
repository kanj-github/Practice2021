class SubtreeWithMaxDistance {

    private lateinit var tree: Array<ArrayList<Int>>
    private lateinit var V: BooleanArray
    private lateinit var result: IntArray

    fun countSubgraphsForEachDiameter(n: Int, edges: Array<IntArray>): IntArray {

        result = IntArray(n-1) { 0 }
        tree = Array(n) { ArrayList<Int>(3) }
        V = BooleanArray(n + 1) { false }

        for (e in edges) {
            val a = e[0] - 1
            val b = e[1] - 1
            tree[a].add(b)
            tree[b].add(a)
        }

        computeDiameters(0)
        return result
    }

    private fun computeDiameters(nodeIdx: Int): List<Info> {
        println("computeDiameters $nodeIdx")
        val children = tree[nodeIdx]
        val subTrees = ArrayList<Info>()
        subTrees.add(Info(0, 0))
        V[nodeIdx] = true

        for (c in children) {

            if (V[c]) {
                println("computeDiameters $nodeIdx - $c was visited")
                continue
            }

            val numSubTrees = subTrees.size
            println("computeDiameters $nodeIdx - compute for $c - numSubTrees = $numSubTrees")
            val childSubTrees = computeDiameters(c)
            println("computeDiameters $nodeIdx - compute for $c gave ${childSubTrees.size} items")

            for (childSubTree  in childSubTrees) {
                for (i in 0 until numSubTrees) {
                    val subTree = subTrees[i]
                    val newDepth = maxOf(subTree.depth, 1 + childSubTree.depth)
                    val newDiameter = maxOf(subTree.diameter, 1 + childSubTree.depth + subTree.depth, childSubTree.diameter)
                    println("newDepth = $newDepth, newDiameter = $newDiameter, created by $c")
                    val info = Info(newDepth, newDiameter)
                    result[newDiameter - 1] += 1
                    subTrees.add(info)
                }
            }
        }

        println("computeDiameters $nodeIdx returning ${subTrees.size}")
        return subTrees
    }

    // depth = max depth in DFS tree of any node in a subtree this describes.
    data class Info(val depth: Int, val diameter: Int)
}

fun main() {
    val x = SubtreeWithMaxDistance()
    val edges = arrayOf(intArrayOf(1,2),intArrayOf(2,3),intArrayOf(2,4))
    x.countSubgraphsForEachDiameter(4, edges).forEach {
        print("$it ")
    }
}

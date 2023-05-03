class PairMatchingUsingMaxFlow {

    private val R = mutableMapOf<Int, ArrayList<Edge>>()

    fun maxPairMatching(connections: List<Pair<Int, Int>>): List<Pair<Int, Int>> {

        val lefts = mutableSetOf<Int>()
        val rights = mutableSetOf<Int>()

        connections.forEach {

            val connectionEdge = Edge(it.second, 1)
            val adj = R[it.first]?.apply { add(connectionEdge) } ?: arrayListOf(connectionEdge)
            R[it.first] = adj

            lefts.add(it.first)
            rights.add(it.second)
        }

        val srcAdj = ArrayList<Edge>()
        lefts.forEach {
            srcAdj.add(Edge(it, 1))
        }
        R[-1] = srcAdj

        rights.forEach {
            R[it] = arrayListOf(Edge(-2, 1))
        }

        val path = arrayListOf<Int>()

        while (true) {

            val found = findPathBfs(path)

            if (!found) {
                println("No path")
                break
            }

            println("path")

            for (i in 0 until path.size - 1) {
                val a = path[i]
                val b = path[i+1]
                createEdge(a, b)
            }

            path.clear()
        }

        val ans = ArrayList<Pair<Int, Int>>()
        R[-2]?.let { tAdj ->
            tAdj.forEach { right ->
                R[right.to]?.let { adj ->
                    val edges = adj.filter { it.c != 0 && it.to != -2}
                    if (edges.size > 1) {
                        throw RuntimeException("$right matched with $edges")
                    } else if (edges.size == 1) {
                        ans.add(Pair(edges[0].to, right.to))
                    }
                }
            }
        }

        return ans
    }

    private fun createEdge(a: Int, b: Int) {

        R[a]?.first { it.to == b }?.apply {
            c = 0
        }

        R[b]?.removeAll { it.to == a }
        val residual = Edge(a, 1)
        val adj = R[b]?.apply { add(residual) } ?: arrayListOf(residual)
        R[b] = adj
    }

    private fun findPathBfs(path: ArrayList<Int>): Boolean {

        val parent = mutableMapOf<Int, Int>()
        val nodes = arrayListOf(-1)
        parent[-1] = -1

        while (nodes.isNotEmpty()) {
            for (i in 0 until nodes.size) {
                val node = nodes.removeAt(0)
                if (node == -2) {
                    setPath(parent, path)
                    return true
                }

                val edges = R[node]
                edges ?: continue

                for (j in 0 until edges.size) {

                    val e = edges[j]
                    if (e.c <= 0 || parent[e.to] != null) {
                        continue
                    }

                    nodes.add(e.to)
                    parent[e.to] = node
                }
            }
        }

        return false
    }

    private fun setPath(parent: MutableMap<Int, Int>, path: ArrayList<Int>) {
        var c = -2
        while (c != -1) {
            path.add(0, c)
            c = parent[c] ?: -1
        }
        path.add(0, -1)
    }

    // Assumes -1 already added to path
    private fun findPathDfs(n: Int, path: ArrayList<Int>): Boolean {

        if (n == -2) {
            return true
        }

        val edges = R[n]
        edges ?: return false

        for (i in 0 until edges.size) {

            val e = edges[i]
            if (e.c <= 0 || path.contains(e.to)) {
                continue
            }

            path.add(e.to)
            if (findPathDfs(e.to, path)) {
                return true
            }
            path.removeLast()
        }

        return false
    }

    class Edge(val to: Int, var c: Int)
}

fun main() {
    val connections = listOf(Pair(1,7), Pair(1,9), Pair(2,8), Pair(2,10), Pair(3,7), Pair(3,10),
        Pair(4,9), Pair(4,11), Pair(5,11), Pair(6,11), Pair(6,12), Pair(21,27), Pair(21,29), Pair(22,28),
        Pair(22,30), Pair(23,27), Pair(23,30), Pair(24,29), Pair(24,31), Pair(25,31), Pair(26,31), Pair(26,32))
    var time = System.currentTimeMillis()
    val pairs = PairMatchingUsingMaxFlow().maxPairMatching(connections)
    time = System.currentTimeMillis() - time
    pairs.forEach { println("${it.first} - ${it.second}") }
    println("Took $time ms")
}

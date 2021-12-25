class NaryNode(val value: Int, val children: MutableList<NaryNode> = ArrayList())

class Query(val u: Int, val c: Char)

fun countOfNodes(root: NaryNode, queries: List<Query>, s: String): Array<Int> {
    val output = ArrayList<Int>()
    queries.forEach {
        output.add(query(root, it, s))
    }
    return output.toTypedArray()
}

fun query(root: NaryNode, query: Query, s: String) : Int {

    val numbersToSearch = ArrayList<Int>()
    s.forEachIndexed { index, c ->
        if (c == query.c) {
            numbersToSearch.add(index + 1)
        }
    }

    return getSearchStartNode(root, query.u)?.let {
        countNodesWithValueIn(it, numbersToSearch)
    } ?: 0
}

fun countNodesWithValueIn(node: NaryNode, numbersToSearch: List<Int>): Int {

    var count = 0

    if (numbersToSearch.contains(node.value)) {
        count++
    }

    node.children.forEach {
        count += countNodesWithValueIn(it, numbersToSearch)
    }

    return count
}

fun getSearchStartNode(root: NaryNode, u: Int): NaryNode? {

    if (root.value == u) {
        return root
    }

    root.children.forEach {
        val found = getSearchStartNode(it, u)
        if (found != null) {
            return it
        }
    }

    return null
}

fun printIntArray(array: Array<Int>) {

    if (array.isEmpty()) {
        println("Empty")
    }

    array.forEach { print("$it ") }
    println()
}

fun main() {

    val root = NaryNode(1)
    root.children.add(NaryNode(2))
    root.children.add(NaryNode(3))

    val queries1 = listOf(Query(1, 'a'))
    val output1 = countOfNodes(root, queries1, "aba")
    printIntArray(output1)

    root.children.add(NaryNode(7))
    root.children[0].children.add(NaryNode(4))
    root.children[0].children.add(NaryNode(5))
    root.children[1].children.add(NaryNode(6))

    val queries2 = listOf(Query(1, 'a'), Query(2, 'b'), Query(3, 'a'))
    val output2 = countOfNodes(root, queries2, "abaacab")
    printIntArray(output2)
}

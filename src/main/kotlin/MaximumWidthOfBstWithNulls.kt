// https://leetcode.com/problems/maximum-width-of-binary-tree/

// Width of a level is not defined clearly. I am assuming 2^i for i in 0 to (depth - 1).
// For the last level width = 2 * number of parents
// It's not the correct definition apparently

// Got the definition of width from a solution. It is based on "proper index" of the node in a full BST.
fun widthOfBinaryTree(root: BinaryNode<Int>?): Int {

    root ?: return  0

    val level = arrayListOf(Pair(root, 0))
    var left = 0
    var right = 0
    var maxWidth = 0

    while(level.isNotEmpty()) {

        val lSize = level.size
        for (i in 0 until lSize) {

            val pair = level.removeAt(0)
            val node = pair.first
            val properIndex = pair.second

            if (i == 0) {
                left = properIndex
            }

            if (i == lSize - 1) {
                right = properIndex
            }

            node.left?.let {
                level.add(Pair(it, properIndex * 2 + 1))
            }

            node.right?.let {
                level.add(Pair(it, properIndex * 2 + 2))
            }
        }

        maxWidth = maxOf(maxWidth, right - left + 1)
    }

    return maxWidth
}

fun main() {

    val one = BinaryNode(1)
    val two = BinaryNode(2)
    val three = BinaryNode(3)
    val four = BinaryNode(4)
    val five = BinaryNode(5)
    val six = BinaryNode(6)
    val seven = BinaryNode(7)

    one.right = three
    three.right = five
    one.left = two
    two.left = four
    four.left = six
    five.right = seven

    println(widthOfBinaryTree(one))
}

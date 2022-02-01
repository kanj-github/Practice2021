import kotlin.math.max

class BstNode(val value: Int, var left: BstNode? = null, var right: BstNode? = null)

fun getHeight(root: BstNode): Int {
    val leftHeight = root.left?.let { getHeight(it) } ?: 0
    val rightHeight = root.right?.let { getHeight(it) } ?: 0
    return max(rightHeight, leftHeight) + 1
}

fun main(args : Array<String>) {

}

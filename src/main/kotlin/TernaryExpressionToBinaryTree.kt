import java.util.Stack

// Use stack, create binary tree

class BinaryNode<T>(val value: T, var left: BinaryNode<T>? = null, var right: BinaryNode<T>? = null) {

    fun printPreorder() {
        println("$value - left child ${left?.value} right child ${right?.value}")
        left?.printPreorder()
        right?.printPreorder()
    }
}

fun convertExpression(expr: String): BinaryNode<Char>? {

    if (expr.isEmpty() || expr.startsWith('?') || expr.startsWith(':')) {
        return null
    }

    val node = BinaryNode(expr[0])
    var i = 1

    val stack = Stack<BinaryNode<Char>>()
    stack.push(node)

    while (i < expr.length) {
        val symbol = expr[i]
        val nextNode = BinaryNode(expr[++i])
        if (symbol == '?') {
            stack.peek().left = nextNode
            stack.push(nextNode)
        } else if (symbol == ':') {
            stack.pop()
            stack.peek().right = nextNode
        }
        i++
    }

    return node
}

fun main() {
    val root = convertExpression("a?b?c:d:e")
    root?.printPreorder() ?: println("Empty")
}

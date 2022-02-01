import java.lang.StringBuilder

//https://leetcode.com/problems/simplify-path/

// State machine for parsing. Double linked list to store parsed data.

class DLNode(val name: String, val parent: DLNode?, var child: DLNode? = null)

class DoubleLinkedList(val root: DLNode)

abstract class State {
    val stringBuilder = StringBuilder("")
    abstract fun accept(c: Char): State
    abstract fun getToken(): String
}

class Initial : State() {
    override fun accept(c: Char): State {
        return when(c) {
            '/' -> this
            '.' -> SingleDot()
            else -> Name("$c")
        }
    }

    override fun getToken(): String {
        return ""
    }
}

class SingleDot : State() {
    override fun accept(c: Char): State {
        return when(c) {
            '/' -> Done(".")
            '.' -> DoubleDot()
            else -> Name(".$c")
        }
    }

    override fun getToken(): String {
        return "."
    }
}

class DoubleDot : State() {
    override fun accept(c: Char): State {
        return when(c) {
            '/' -> Done("..")
            '.' -> Name("...")
            else -> Name("..$c")
        }
    }

    override fun getToken(): String {
        return ".."
    }
}

class Name(name: String) : State() {

    init {
        stringBuilder.append(name)
    }

    override fun accept(c: Char): State {
        return when(c) {
            '/' -> Done(stringBuilder.toString())
            else -> {
                stringBuilder.append(c)
                this
            }
        }
    }

    override fun getToken(): String {
        return stringBuilder.toString()
    }
}

class Done(val name: String) : State() {
    override fun accept(c: Char): State {
        TODO("Not yet implemented")
    }

    override fun getToken(): String {
        return name
    }
}

fun simplifyPath(path: String): String {

    var done = false
    var currentPath = path

    var currentNode = DLNode("/", null)
    val dl = DoubleLinkedList(currentNode)

    while (!done) {

        val parsed = getNextToken(currentPath)
        //println("Parsed ${parsed.first} ${parsed.second}")
        done = parsed.first.isEmpty()

        if (parsed.second == "..") {
            currentNode = currentNode.parent ?: currentNode
        } else if (parsed.second.isNotEmpty() && parsed.second != ".") {
            val node = DLNode(parsed.second, currentNode)
            currentNode.child = node
            currentNode = node
        }

        currentPath = parsed.first
    }

    currentNode.child = null

    dl.root.child?.let {
        return buildString {
            var node: DLNode? = it
            while (node != null) {
                append('/')
                append(node.name)
                node = node.child
            }
        }
    } ?: return "/"
}

// Return updated path & token
fun getNextToken(path: String): Pair<String, String> {

    var i = 0
    var state: State = Initial()

    while (i < path.length && state !is Done) {
        state = state.accept(path[i])
        i++
    }

    return if (i < path.length) {
        Pair(path.substring(i), state.getToken())
    } else {
        Pair("", state.getToken())
    }
}

fun main() {
    println(simplifyPath("/a//b////c/d//././/.."))
    println(simplifyPath("/jaxJp/././"))
}

// https://leetcode.com/problems/implement-trie-prefix-tree/description
// Accepted
class TrieInefficient {

    private val root = TrieNode()

    fun insert(word: String) {

        if (word.isEmpty()) {
            root.endsHere = true
            return
        }

        var curr = root
        for (c in word) {
            val cNode = curr.children[c]
            if (cNode == null) {
                val add = TrieNode()
                curr.children[c] = add
                curr = add
            } else {
                curr = cNode
            }
        }
        curr.endsHere = true
    }

    fun search(word: String): Boolean {

        if (word.isEmpty()) {
            return root.endsHere
        }

        var curr = root
        for (c in word) {
            val cNode = curr.children[c] ?: return false
            curr = cNode
        }

        return curr.endsHere
    }

    fun startsWith(prefix: String): Boolean {

        if (prefix.isEmpty()) {
            return true
        }

        var curr = root
        for (c in prefix) {
            val cNode = curr.children[c] ?: return false
            curr = cNode
        }

        return true
    }

    private class TrieNode {
        val children = mutableMapOf<Char, TrieNode>()
        var endsHere = false
    }
}

fun main() {

    val ti = TrieInefficient()

    ti.insert("apple")
    println(ti.search("apple"))   // return True
    println(ti.search("app"))    // return False
    println(ti.startsWith("app")) // return True
    ti.insert("app")
    println(ti.search("app"))
    println(ti.startsWith("apple"))
}

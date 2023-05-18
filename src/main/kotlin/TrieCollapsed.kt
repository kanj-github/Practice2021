// https://leetcode.com/problems/implement-trie-prefix-tree/description
// Gave wrong answer
class TrieCollapsed {

    private val root = TrieNode("")

    fun insert(word: String) {

        if (word.isEmpty()) {
            root.endsHere = true
            return
        }

        var curr = root
        var matchCount = 0
        var matchedChild: TrieNode? = null
        var wordPos = 0

        while(wordPos < word.length) {

            matchCount = 0
            matchedChild = null

            for (c in curr.children) {
                val mC = c.matchString(word, wordPos)
                if (mC != 0) {
                    matchCount = mC
                    matchedChild = c
                    break
                }
            }

            val child = matchedChild
            if (child == null) {
                val tN = TrieNode(word.substring(wordPos))
                tN.endsHere = true
                curr.children.add(tN)
                break
            }

            if (child.length() == matchCount) {
                if (wordPos + matchCount == word.length) {
                    child.endsHere = true
                    break
                } else if (wordPos + matchCount < word.length) {
                    wordPos += matchCount
                    curr = child
                    continue
                    /*val tN = TrieNode(word.substring(wordPos + matchCount))
                    tN.endsHere = true
                    child.children.add(tN)
                    break*/
                }
            }

            // Child has a prefix that matches a prefix of word[pos:]
            val tN = child.makeChildOf(matchCount)
            curr.children.remove(child)
            curr.children.add(tN)
            if (wordPos + matchCount == word.length) {
                tN.endsHere = true
            } else if (wordPos + matchCount < word.length) {
                val remainingTN = TrieNode(word.substring(wordPos + matchCount))
                remainingTN.endsHere = true
                tN.children.add(remainingTN)
            }
            break
        }
    }

    fun search(word: String): Boolean {

        if (word.isEmpty()) {
            return root.endsHere
        }

        var curr = root
        var wordPos = 0
        while(wordPos < word.length) {

            var matchCount = 0
            var matchedChild: TrieNode? = null
            for (c in curr.children) {
                val mC = c.matchString(word, wordPos)
                if (mC != 0) {
                    matchCount = mC
                    matchedChild = c
                    break
                }
            }

            val child = matchedChild ?: return false
            if (matchCount < child.length()) {
                return false
            } else if (wordPos + matchCount == word.length && child.endsHere) {
                // matchCount == child.length() is implied
                return true
            }

            curr = child
            wordPos += matchCount
        }

        return false
    }

    fun startsWith(prefix: String): Boolean {

        if (prefix.isEmpty()) {
            return true
        }

        var curr = root
        var wordPos = 0
        while(wordPos < prefix.length) {

            var matchCount = 0
            var matchedChild: TrieNode? = null
            for (c in curr.children) {
                val mC = c.matchString(prefix, wordPos)
                if (mC != 0) {
                    matchCount = mC
                    matchedChild = c
                    break
                }
            }

            val child = matchedChild ?: return false

            if (wordPos + matchCount == prefix.length) {
                return true
            }

            curr = child
            wordPos += matchCount
        }

        return false
    }

    fun print() {
        root.print()
    }

    private class TrieNode (private var str: String) {

        var endsHere = false
        val children = arrayListOf<TrieNode>()

        // Make this node a child of a new node containing the first len characters of str
        fun makeChildOf(len: Int): TrieNode {
            val tN = TrieNode(str.substring(0, len))
            str = str.substring(len, str.length)
            tN.children.add(this)
            return tN
        }

        // Return the number of matching characters in word[from:] and str
        fun matchString(word: String, from: Int): Int {
            var i = 0
            while (i < str.length && from + i < word.length) {
                if (str[i] != word[from + i]) {
                    return i
                }
                i++
            }

            return i
        }

        fun length() = str.length

        fun print() {
            val line = buildString {
                append(str)
                append(" -> ")
                children.forEach {
                    append(it.str)
                    append(", ")
                }
                append(endsHere)
            }
            println(line)
            children.forEach {
                it.print()
            }
        }
    }
}

fun main() {
    val tc = TrieCollapsed()
    tc.insert("apple")
    tc.insert("apply")
    tc.insert("application")
    tc.insert("bomb")
    tc.insert("bomber")
    tc.insert("applicant")
    tc.print()
    //println(tc.search("apple"))
    //println(tc.search("app"))
    //println(tc.search("apples"))
    //println(tc.search("applica"))
    println(tc.startsWith("applic"))
    println(tc.startsWith("applx"))
    //println(tc.startsWith("apples"))
    //println(tc.startsWith("xapple"))
    println(tc.startsWith("appli"))
    println(tc.startsWith("applica"))
}

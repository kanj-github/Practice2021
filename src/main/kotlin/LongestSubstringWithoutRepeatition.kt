// https://leetcode.com/problems/longest-substring-without-repeating-characters/

// Sliding window

fun lengthOfLongestSubstring(s: String): Int {

    val charsInWindow = mutableSetOf<Char>()
    var maxsize = 0
    var i = 0

    for (j in s.indices) {

        val c = s[j]

        if (!charsInWindow.contains(c)) {
            charsInWindow.add(c)
            val size = j - i + 1
            if (size > maxsize) {
                maxsize = size
            }
            continue
        }

        while(i <= j && charsInWindow.contains(c)) {
            charsInWindow.remove(s[i])
            i++
        }
        charsInWindow.add(c)

        val size = j - i + 1
        if (size > maxsize) {
            maxsize = size
        }
    }

    return maxsize
}

fun main() {
    println(lengthOfLongestSubstring("pwwkew"))
}

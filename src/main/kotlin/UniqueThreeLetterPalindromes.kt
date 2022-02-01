// https://leetcode.com/problems/unique-length-3-palindromic-subsequences/

// Used memoisation to avoid repeated calculations
class UniqueThreeLetterPalindromes {
    fun countPalindromicSubsequence(s: String): Int {

        val sc = mutableSetOf<Char>()
        val lim = mutableMapOf<Char, Int>()
        val ucm = mutableMapOf<Pair<Int, Int>, Int>()

        var count = 0

        for (i in 0 until s.length - 2) {

            val c = s[i]

            if (sc.contains(c)) {
                continue
            }

            val li = getLastIndexOf(c, s, lim)
            count += getUniqueCharactersBetween(i, li, s, ucm)

            sc.add(c)
        }

        return count
    }

    private fun getLastIndexOf(c: Char, s: String, lim: MutableMap<Char, Int>): Int {

        lim[c]?.let {
            return it
        }

        val i = s.lastIndexOf(c)
        lim[c] = i
        return i
    }

    private fun getUniqueCharactersBetween(s: Int, e: Int, str: String, ucm: MutableMap<Pair<Int, Int>, Int>): Int {

        ucm[Pair(s, e)]?.let {
            return it
        }

        val cs = mutableSetOf<Char>()
        for (i in s+1 until e) {
            cs.add(str[i])
        }

        ucm[Pair(s, e)] = cs.size
        return cs.size
    }
}

// https://leetcode.com/problems/unique-length-3-palindromic-subsequences/

// Used memoisation to avoid repeated calculations
class UniqueThreeLetterPalindromes {

    fun countPalindromicSubsequence(s: String): Int {

        val sc = mutableSetOf<Char>()
        val lim = mutableMapOf<Char, Int>()

        var count = 0

        for (i in 0 until s.length - 2) {

            val c = s[i]

            if (sc.contains(c)) {
                continue
            }

            val li = getLastIndexOf(c, s, lim)

            count += getUniqueCharactersBetween(i, li, s)

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

    private fun getUniqueCharactersBetween(s: Int, e: Int, str: String): Int {

        if (e-s < 2) {
            return 0
        } else if (e-s == 2) {
            return 1
        }

        val cs = mutableSetOf<Char>()
        for (i in s+1 until e) {
            cs.add(str[i])
        }

        return cs.size
    }
}

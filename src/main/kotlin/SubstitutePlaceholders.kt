class SubstitutePlaceholders {

    private val dict = mapOf(
        Pair("X", "123"),
        Pair("y", "%z% and %cv% 5%"),
        Pair("z", "bac"),
        Pair("cv", "-+%blurb%+-"),
        Pair("blurb", "hgfjhgf"),
    )

    fun sub(pattern: String): String {
        val chars = arrayListOf<Char>()
        subInternal(pattern, chars)
        return String(chars.toCharArray())
    }

    private fun subInternal(pattern: String, chars: ArrayList<Char>) {
        var i = 0
        while (i < pattern.length) {
            val ch = pattern[i]
            if (ch == '%') {
                val closesAt = pattern.indexOf('%', i + 1) // Handle -1
                if (closesAt == -1) {
                    chars.add('%')
                    i++
                    continue
                }
                val substitutionTarget = pattern.substring(i + 1, closesAt)
                dict[substitutionTarget]?.let {
                    subInternal(it, chars)
                } ?: substitutionTarget.forEach { chars.add(it) }
                i = closesAt + 1
            } else {
                chars.add(ch)
                i++
            }
        }
    }
}

fun main() {
    val s = SubstitutePlaceholders()
    println(s.sub("--%X%_%y%--"))
}

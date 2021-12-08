fun minLengthSubstring(s: String, t: String): Int {

    if (s.length < t.length) {
        return -1
    }

    val tMap = mutableMapOf<Char, Int>()
    t.forEach { ch -> tMap[ch] = tMap[ch]?.let { it + 1 } ?: 1 }

    var i = 0
    var j = 0
    var count = tMap.size

    var minWindow = -1
    var start = -1
    var end = -1

    while (j < s.length) {

        val ch = s[j]

        tMap[ch]?.let {
            tMap[ch] = it - 1
            if (it - 1 == 0) {
                count--
            }
        }

        if (count == 0) {

            while (count == 0) {

                val len = j - i + 1
                if (minWindow == -1 || len < minWindow) {
                    minWindow = len
                    start = i
                    end = j
                }

                tMap[s[i]]?.let {
                    tMap[s[i]] = it + 1
                    if (it + 1 > 0) {
                        count++
                    }
                }

                i++
            }
        }

        j++
    }

    if (start != -1) {
        println(s.substring(start, end + 1))
    }
    return minWindow
}

fun main(args : Array<String>) {

    var output = minLengthSubstring("ADOBECODEBANC", "ABC")
    println(output)

    output = minLengthSubstring("this is a test string", "tist")
    println(output)

    output = minLengthSubstring("dcbefebce", "fd")
    println(output)

    output = minLengthSubstring("bfbeadbcbcbfeaaeefcddcccbbbfaaafdbebedddf", "cbccfafebccdccebdd")
    println(output)
}

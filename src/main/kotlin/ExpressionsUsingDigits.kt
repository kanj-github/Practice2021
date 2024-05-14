class ExpressionsUsingDigits {

    private var results = arrayListOf<String>()

    fun addOperators(num: String, target: Int): List<String> {

        if (num.length == 1) {
            return if (num[0].toInt() - '0'.toInt() == target) listOf(num) else results
        }

        val powerOfFour = num.length - 1
        var maxRank = 1
        for (i in 0 until powerOfFour) {
            maxRank *= 4
        }

        val chars = ArrayList<Char>(2 * num.length - 1)
        for (rank in 0 until maxRank) {
            chars.clear()
            for (i in 0 until num.length - 1) {
                chars.add(num[i])
                operatorAfter(i, rank)?.let {
                    chars.add(it)
                }
            }
            chars.add(num.last())
            if (isValid(chars, target)) {
                results.add(String(chars.toCharArray()))
            }
        }

        return results
    }

    private fun isValid(chars: List<Char>, target: Int): Boolean {

        val value = calculate(chars)
        value ?: return false

        return value == target.toLong()
    }

    private fun calculate(chars: List<Char>): Long? {

        var exp1 = 0L
        var exp2 = 1L
        var op: Char? = null
        var i = 0

        while (i < chars.size) {
            val nextNum = readNumber(chars, i)
            nextNum ?: return null

            i = nextNum.second

            if (i == chars.size) {
                val op1 = op
                if (op1 != null) {
                    exp1 = performOp(op1, exp1, exp2 * nextNum.first)
                } else {
                    exp1 = exp2 * nextNum.first
                }
                break
            }

            if (chars[i] == '+' || chars[i] == '-') {
                val operation = op
                if (operation == null) {
                    exp1 = exp2 * nextNum.first
                } else {
                    exp1 = performOp(operation, exp1, exp2 * nextNum.first)
                }
                exp2 = 1L
                op = chars[i++]
            } else if (chars[i] == '*') {
                exp2 *= nextNum.first
                i++
            }
        }

        return exp1
    }

    private fun performOp(ch: Char, a: Long, b: Long): Long {
        return when (ch) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            else -> error("Unknown op")
        }
    }

    private fun readNumber(chars: List<Char>, s: Int): Pair<Long, Int>? {

        if (chars[s] == '0' && s + 1 < chars.size && chars[s + 1].isDigit()) {
            return null
        }

        var num = 0L
        var i = s
        while (i < chars.size) {
            val ch = chars[i]
            if (ch.isDigit()) {
                num *= 10L
                num += (ch.code - '0'.code).toLong()
            } else {
                break
            }
            i++
        }

        return Pair(num, i)
    }

    private fun operatorAfter(i: Int, rank: Int): Char? {
        val mask = 3.shl(i * 2)
        val code = rank.and(mask).shr(i * 2)
        return when (code) {
            1 -> '+'
            2 -> '-'
            3 -> '*'
            else -> null
        }
    }

    private fun isOperator(ch: Char): Boolean {
        return ch == '+' || ch == '-' || ch == '*'
    }
}

fun main() {
    val sol = ExpressionsUsingDigits()
    val results = sol.addOperators("103", 4)
    println("$results")
}

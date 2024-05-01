class ExpressionsUsingDigits {

    private var results = arrayListOf<String>()

    fun addOperators(num: String, target: Int): List<String> {

        if (num.length == 1) {
            return if (num[0].code - '0'.code == target) listOf(num) else results
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

        val exps = getExpressions(chars)
        exps ?: return false

        // Multiply first
        val multipliedExps = performMultiplications(exps)

        // Add and subtract now
        val firstExp = multipliedExps[0]
        val firstN = if (firstExp is Exp.Num) firstExp.n else null
        firstN ?: return false

        var total: Long = firstN

        for (i in 2 until multipliedExps.size) {
            val exp = multipliedExps[i]
            if (exp !is Exp.Num) {
                continue
            }
            val prev = multipliedExps[i - 1]
            if (prev !is Exp.Op) {
                continue
            }

            when (prev.ch) {
                '+' -> total += exp.n
                '-' -> total -= exp.n
            }
        }

        return total == target.toLong()
    }

    private fun getExpressions(chars: List<Char>): List<Exp>? {
        var i = 0
        val exps = arrayListOf<Exp>()
        while (i < chars.size) {
            val nextOpI = findOperatorAfter(i, chars)
            val lastDigitI = if (nextOpI == -1) chars.size - 1 else nextOpI - 1
            val num = decodeToInt(chars, i, lastDigitI)
            num ?: return null
            exps.add(Exp.Num(num))
            if (nextOpI != -1) {
                exps.add(Exp.Op(chars[nextOpI]))
                i = nextOpI + 1
            } else {
                break
            }
        }
        return exps
    }

    private fun performMultiplications(exps: List<Exp>): List<Exp> {
        val multipliedExps = arrayListOf(exps[0])
        for (i in 1 until exps.size) {
            val prev = exps[i - 1]
            val exp = exps[i]
            if (prev is Exp.Op && prev.ch == '*') {
                val lastInM = multipliedExps.last()
                if (lastInM is Exp.Num && exp is Exp.Num) {
                    lastInM.multiplyBy(exp.n)
                }
            } else if (exp is Exp.Num || (exp is Exp.Op && exp.ch != '*')) {
                multipliedExps.add(exp)
            }
        }
        return multipliedExps
    }

    private fun decodeToInt(chars: List<Char>, s: Int, e: Int): Long? {

        if (chars[s] == '0') {
            // 0 is acceptable, leading 0s are not
            return if (s == e) 0 else null
        }

        var powerOfTen = 1
        var num = 0L
        for (i in e downTo s) {
            num += powerOfTen * (chars[i].code - '0'.code)
            powerOfTen *= 10
        }

        return num
    }

    private fun findOperatorAfter(i: Int, chars: List<Char>): Int {
        if (i >= chars.size - 1) {
            return -1
        }

        for (j in i + 1 until chars.size) {
            if (isOperator(chars[j])) {
                return j
            }
        }

        return -1
    }

    private fun isOperator(ch: Char): Boolean {
        return ch == '+' || ch == '-' || ch == '*'
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

    sealed class Exp {
        class Num(var n: Long) : Exp() {
            fun multiplyBy(x: Long) {
                n *= x
            }
        }

        class Op(val ch: Char) : Exp()
    }
}

fun main() {
    val sol = ExpressionsUsingDigits()
    val results = sol.addOperators("103", 4)
    println("$results")
}

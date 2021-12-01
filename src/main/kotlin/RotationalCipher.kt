fun rotationalCipher(input: String, rotationFactor: Int): String {

    val firstU = 'A'.code
    val lastU = 'Z'.code
    val firstL = 'a'.code
    val lastL = 'z'.code
    val firstD = '0'.code
    val lastD = '9'.code

    val sb = StringBuilder()
    input.forEach { c ->
        when (val code = c.code) {
            in firstU..lastU -> {
                sb.append(getRotatedAlphabet(firstU, code, rotationFactor))
            }
            in firstL..lastL -> {
                sb.append(getRotatedAlphabet(firstL, code, rotationFactor))
            }
            in firstD..lastD -> {
                sb.append(getRotatedDigit(firstD, code, rotationFactor))
            }
            else -> sb.append(c)
        }
    }

    return sb.toString()
}

fun getRotatedAlphabet(firstCode: Int, alphabetCode: Int, rotationFactor: Int): Char {
    val rotatedCode = (((alphabetCode - firstCode) + rotationFactor) % 26) + firstCode
    return rotatedCode.toChar()
}

fun getRotatedDigit(firstCode: Int, digitCode: Int, rotationFactor: Int): Char {
    val rotatedCode = (((digitCode - firstCode) + rotationFactor) % 10) + firstCode
    return rotatedCode.toChar()
}

fun main(args: Array<String>) {
    val output = rotationalCipher("abcdefghijklmNOPQRSTUVWXYZ0123456789", 39)
    assert(output == "nopqrstuvwxyzABCDEFGHIJKLM9012345678")
    println(output)
}

import java.util.PriorityQueue

data class XY(val x: Int, val y: Int) {
    override fun toString() = "$x $y"
}

val minPq = PriorityQueue<XY> { a, b -> a.x - b.x }
val maxPq = PriorityQueue<XY> {a, b -> b.x - a.x}

fun main() {

    val xys = arrayOf(XY(1,4), XY(2,3), XY(3,2), XY(4,1))

    xys.forEach {
        maxPq.offer(it)
        minPq.offer(it)
    }

    while (maxPq.isNotEmpty()) {
        println(maxPq.poll())
    }

    while (minPq.isNotEmpty()) {
        println(minPq.poll())
    }

    xys.sortedWith(compareBy ({ it.y }, {it.x})).forEach{
        println(it)
    }
}

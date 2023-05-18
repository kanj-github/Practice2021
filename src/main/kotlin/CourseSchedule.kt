// https://leetcode.com/problems/course-schedule-ii
class CourseSchedule {

    private lateinit var result: ArrayList<Int>
    private lateinit var entry: IntArray
    private lateinit var exit: IntArray
    private lateinit var adj: Array<ArrayList<Int>>

    private var time = 0

    fun findOrder(numCourses: Int, prerequisites: Array<IntArray>): IntArray {

        result = arrayListOf()

        entry = IntArray(numCourses) { -1 }
        exit = IntArray(numCourses) { -1 }

        adj = Array(numCourses) { arrayListOf() }

        for (p in prerequisites) {
            adj[p[0]].add(p[1])
        }

        for (i in 0 until numCourses) {
            if (entry[i] == -1) {
                if (!dfs(i)) {
                    return intArrayOf()
                }
            }
        }

        return result.toIntArray()
    }

    private fun dfs(n: Int): Boolean {

        time++
        entry[n] = time

        for (c in adj[n]) {

            if (entry[c] != -1 && exit[c] == -1) {
                return false
            }

            if (entry[c] != -1 && exit[c] != -1 && entry[c] < time && exit[c] <= time) {
                continue
            }

            if (!dfs(c)) {
                return false
            }
        }

        time++
        exit[n] = time
        result.add(n)
        return true
    }
}

fun main() {
/*
    val prerequisites = arrayOf(
        intArrayOf(1,0),
        intArrayOf(2,0),
        intArrayOf(0,1),
        intArrayOf(3,2),
    )*/

    val prerequisites = arrayOf(
        intArrayOf(0,1),
    )

    val cs = CourseSchedule()
    val order = cs.findOrder(2, prerequisites)
    order.forEach {
        print("$it ")
    }
    println()
}

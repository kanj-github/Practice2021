class MinArrowToShootBalloons {

    fun findMinArrowShots(points: Array<IntArray>): Int {

        val groups = arrayListOf<BGroup>()
        var group: BGroup? = null

        val sorted = points.sortedWith(compareBy({ it[0] }, { it[1] }))
        for (p in sorted) {
            val curr = group
            if (curr == null) {
                group = BGroup(p)
                continue
            }

            if (!curr.merge(p)) {
                groups.add(curr)
                group = BGroup(p)
            }
        }

        group?.let {
            groups.add(it)
        }

        return groups.size
    }

    private class BGroup(
        first: IntArray
    ) {
        private var intersection = first

        fun merge(balloon: IntArray): Boolean {

            val inter = getIntersection(intersection, balloon)
            inter ?: return false

            intersection = inter
            return true
        }

        private fun getIntersection(a: IntArray, b: IntArray): IntArray? {
            val compare = if (a[1] < b[0]) {
                -1
            } else if (a[0] > b[1]) {
                1
            } else {
                0
            }

            if (compare != 0) {
                return null
            }

            return intArrayOf(maxOf(a[0], b[0]), minOf(a[1], b[1]))
        }
    }
}

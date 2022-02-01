// https://leetcode.com/problems/filter-restaurants-by-vegan-friendly-price-and-distance/

class FilterAndRank {

    class Node(val restId: Int, val rating: Int, var left: Node? = null, var right: Node? = null) {

        fun isHigher(n: Node): Boolean {

            return if (n.rating != rating) {
                n.rating > rating
            } else {
                n.restId > restId
            }
        }
    }

    class BT(private val root: Node) {

        fun insert(restId: Int, rating: Int) {
            val n = Node(restId, rating)
            insertRec(n, root)
        }

        private fun insertRec(node: Node, parent: Node) {

            if (parent.isHigher(node)) {

                if (parent.left == null) {
                    parent.left = node
                } else {
                    parent.left?.let { insertRec(node, it) }
                }
            } else {

                if (parent.right == null) {
                    parent.right = node
                } else {
                    parent.right?.let { insertRec(node, it) }
                }

            }
        }

        fun getInOrder(): ArrayList<Int> {
            val list = ArrayList<Int>()
            inOrderRec(root, list)
            return list
        }

        private fun inOrderRec(node: Node, list: ArrayList<Int>) {

            node.left?.let {
                inOrderRec(it, list)
            }

            list.add(node.restId)

            node.right?.let {
                inOrderRec(it, list)
            }
        }
    }

    fun filterRestaurants(restaurants: Array<IntArray>, veganFriendly: Int, maxPrice: Int, maxDistance: Int): List<Int> {

        var tree: BT? = null

        for (r in restaurants) {

            if ((veganFriendly == 1 && r[2] != 1) || (r[3] > maxPrice) || (r[4] > maxDistance)) {
                continue
            }

            if (tree == null) {
                tree = BT(Node(r[0], r[1]))
                continue
            }

            tree.insert(r[0], r[1])
        }

        return tree?.getInOrder() ?: emptyList()
    }
}

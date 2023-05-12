class LowestCommonAncestor {

    fun lowestCommonAncestor(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {

        if (root == null || p == null || q == null) {
            return null
        }

        if (p == root || q == root) {
            return root
        }

        val pPath = arrayListOf(root)
        findPathToNode(root, p.v, pPath)

        val qPath = arrayListOf(root)
        findPathToNode(root, q.v, qPath)

        val minLen = minOf(pPath.size, qPath.size)

        for (i in minLen - 1 downTo 0) {
            val x = pPath[i]
            val y = qPath[i]
            if (x.v == y.v) {
                return x
            }
        }

        return null
    }

    private fun findPathToNode(p: TreeNode, value: Int, path:ArrayList<TreeNode>): Boolean {

        if (p.v == value) {
            return true
        }

        p.left?.let {
            path.add(it)
            if (findPathToNode(it, value, path)) {
                return true
            }
            path.removeAt(path.size - 1)
        }

        p.right?.let {
            path.add(it)
            if (findPathToNode(it, value, path)) {
                return true
            }
            path.removeLast()
        }

        return false
    }
}

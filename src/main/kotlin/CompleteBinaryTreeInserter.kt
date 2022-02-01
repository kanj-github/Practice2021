// https://leetcode.com/problems/complete-binary-tree-inserter/

class TreeNode(var v: Int) {
  var left: TreeNode? = null
  var right: TreeNode? = null
}

// It will be initialised with a complete binary tree. Not just a root node.
class CBTInserter(private val root: TreeNode?) {

    private val list = ArrayList<TreeNode>()

    init {
        root?.let {

            val q = ArrayList<TreeNode>()
            q.add(it)

            while (q.isNotEmpty()) {

                val s = q.size

                for (i in 0 until s) {
                    val n = q[0]
                    list.add(n)
                    n.left?.let {
                        q.add(it)
                    }
                    n.right?.let {
                        q.add(it)
                    }
                    q.removeAt(0)
                }
            }
        }
    }

    private fun parent(index: Int): Int {
        return if (index <= 0) {
            -1
        } else {
            (index - 1) / 2
        }
    }

    fun insert(v: Int): Int {
        val n = TreeNode(v)
        list.add(n)

        val pI = parent(list.size - 1)

        if (pI == -1) {
            return -1
        }

        val pN = list[pI]

        if (pN.left == null) {
            pN.left = n
        } else {
            pN.right = n
        }

        return pN.v
    }

    fun get_root(): TreeNode? {
        return root
    }
}

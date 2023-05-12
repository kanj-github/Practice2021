import java.util.Stack

// https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description
class ConstructBinaryTree {

    fun fromPreOrderAndInOrder(preorder: IntArray, inorder: IntArray): TreeNode? {

        if (preorder.isEmpty()) {
            return null
        }

        val root = TreeNode(preorder[0])
        attachChildrenFromPreorder(preorder, inorder, 0, root, 0, inorder.size - 1)
        return root
    }

    private fun attachChildrenFromPreorder(preorder: IntArray, inorder: IntArray, pI: Int, p: TreeNode,
                                           fromInO: Int, toInO: Int) {

        val idx = findNodeIndex(inorder, fromInO, toInO, preorder[pI])

        if (idx > fromInO) {
            val lc = TreeNode(preorder[pI + 1])
            p.left = lc
            attachChildrenFromPreorder(preorder, inorder, pI + 1, lc, fromInO, idx - 1)
        }

        if (idx < toInO) {
            val preI = pI + idx - fromInO + 1
            val rc = TreeNode(preorder[preI])
            p.right = rc
            attachChildrenFromPreorder(preorder, inorder, preI, rc, idx + 1, toInO)
        }
    }

    fun fromPostOrderAndInOrder(postorder: IntArray, inorder: IntArray): TreeNode? {

        if (postorder.isEmpty()) {
            return null
        }

        val lastIndex = postorder.size - 1
        val root = TreeNode(postorder[lastIndex])
        attachChildrenFromPostorder(postorder, inorder, lastIndex, root, 0, lastIndex)
        return root
    }

    private fun attachChildrenFromPostorder(postorder: IntArray, inorder: IntArray, pI: Int, p: TreeNode,
                                           fromInO: Int, toInO: Int) {
        //println("Attach children to ${postorder[pI]}, from $fromInO to $toInO")
        val idx = findNodeIndex(inorder, fromInO, toInO, postorder[pI])

        if (idx > fromInO) {
            val postI = pI - (toInO - idx) - 1 // (toInO - idx) is the count in right subtree of post[pI]
            val lc = TreeNode(postorder[postI])
            p.left = lc
            attachChildrenFromPostorder(postorder, inorder, postI, lc, fromInO, idx - 1)
        }

        if (idx < toInO) {
            val postI = pI - 1
            val rc = TreeNode(postorder[postI])
            p.right = rc
            attachChildrenFromPostorder(postorder, inorder, postI, rc, idx + 1, toInO)
        }
    }

    private fun findNodeIndex(inorder: IntArray, from: Int, to: Int, n: Int): Int {

        for (i in from..to) {
            if (inorder[i] == n) {
                return i
            }
        }

        return -1
    }
}

fun main() {

    val preorder = intArrayOf(3,7,33,22)
    val inorder = intArrayOf(9,3,15,20,7)
    val postorder = intArrayOf(9,15,7,20,3)

    val cbt = ConstructBinaryTree()
    val tree = cbt.fromPostOrderAndInOrder(postorder, inorder)
    tree?.print()

    val stack = Stack<Int>()
    stack.push(10)

    while (stack.isNotEmpty()) {
        val n = stack.pop()

    }
}

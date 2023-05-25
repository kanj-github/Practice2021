// https://leetcode.com/problems/kth-largest-element-in-an-array/description
class QuickSelect {
    fun findKthLargest(nums: IntArray, k: Int): Int {

        var s = 0
        var e = nums.size - 1
        val expectedPi = nums.size - k

        while(e >= s) {
            val pi = partition(nums, s, e)
            if (pi < expectedPi) {
                s = pi + 1
            } else if (pi > expectedPi) {
                e = pi - 1
            } else {
                return pi
            }
        }

        return -1
    }

    // Returns index of the pivot after partitioning
    private fun partition(nums: IntArray, s: Int, e: Int): Int {

        if (s == e) {
            return s
        }

        var p = s - 1
        for (i in s until e) {
            if (nums[i] <= nums[e]) {
                swap(nums, i, ++p)
            }
        }

        swap(nums, e, ++p)
        return p
    }

    private fun swap(nums: IntArray, x: Int, y: Int) {

        if (x == y) {
            return
        }

        val temp = nums[x]
        nums[x] = nums[y]
        nums[y] = temp
    }
}

fun main() {
    val qs = QuickSelect()
    println(qs.findKthLargest(intArrayOf(3,2,1,5,6,4), 2))
}

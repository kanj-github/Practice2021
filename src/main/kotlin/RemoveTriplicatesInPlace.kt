// https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/description
class RemoveTriplicatesInPlace {
    fun removeDuplicates(nums: IntArray): Int {

        if (nums.size < 3) {
            return nums.size
        }

        var i = 1 // index of the last valid element
        var l = nums[1]
        var ll = nums[0]
        for (j in 2 until nums.size) {
            val c = nums[j]
            if (ll == c && l == c) {
                continue
            }

            swap(nums, ++i, j)
            ll = l
            l = c
        }

        return i + 1
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

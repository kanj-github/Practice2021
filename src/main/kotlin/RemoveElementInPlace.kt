// https://leetcode.com/problems/remove-element/description
class RemoveElementInPlace {

    fun removeElement(nums: IntArray, v: Int): Int {

        var i = -1 // points to the first v
        for (x in 0 until nums.size) {
            if (nums[x] == v) {
                i = x
                break
            }
        }
        if (i == -1) {
            // No v
            return nums.size
        }

        var j = -1 // points to the last non-v
        for (x in nums.size - 1 downTo 0) {
            if (nums[x] != v) {
                j = x
                break
            }
        }
        if (j == -1) {
            // No non-v
            return 0
        }

        while (true) {

            while (i < nums.size && nums[i] != v) {
                i++
            }

            while (j >= 0 && nums[j] == v) {
                j--
            }

            if (j > i) {
                // swap
                val temp = nums[i]
                nums[i] = nums[j]
                nums[j] = temp
                j--
                i++
            }

            if (j <= i) {
                break
            }
        }

        return nums.filter { it != v }.size
    }

    fun removeElement2(nums: IntArray, v: Int): Int {

        var x = -1 // points to the first v
        var y = nums.size // points to the last non-v

        while (true) {

            val i = nextV(nums, x, v)
            if (i == -1) {
                // No v
                break
            }
            x = i

            val j = lastNonV(nums, y, v)
            if (j == -1) {
                // No non-v
                break
            }
            y = j

            if (y > x) {
                // swap
                val temp = nums[x]
                nums[x] = nums[y]
                nums[y] = temp
            } else {
                break
            }
        }

        nums.forEach { print("$it ") }
        println("\nx = $x, y = $y")
        val ans = if (x == -1) {
            nums.size
        } else if (y == nums.size) {
            0
        } else {
            x
        }

        return ans
    }

    private fun nextV(nums: IntArray, after: Int, v: Int): Int {

        for (i in after + 1 until nums.size) {
            if (nums[i] == v) {
                return i
            }
        }

        return -1
    }

    private fun lastNonV(nums: IntArray, before: Int, v: Int): Int {

        for (i in before - 1 downTo 0) {
            if (nums[i] != v) {
                return i
            }
        }

        return -1
    }
}

fun main() {
    val re = RemoveElementInPlace()
    println(re.removeElement2(intArrayOf(3, 2, 2, 3), 3))
    println(re.removeElement2(intArrayOf(3, 3, 3), 3))
    println(re.removeElement2(intArrayOf(), 4))
    println(re.removeElement2(intArrayOf(4,5), 4))
    println(re.removeElement2(intArrayOf(2, 2, 2), 3))
    println(re.removeElement2(intArrayOf(0, 1, 2, 2, 3, 0, 4, 2), 2))
}

// https://leetcode.com/problems/split-array-with-same-average/

data class MemoKey(val target: Int, val start: Int, val count: Int)

val memo = mutableMapOf<MemoKey, Boolean>()

fun find(nums: IntArray, target: Int, start: Int, count: Int): Boolean {

    if (count == 0) {
        return target == 0
    }

    if (start + count > nums.size) {
        return false
    }

    memo[MemoKey(target, start, count)]?.let {
        return it
    }

    val found = find(nums, target - nums[start], start + 1, count - 1) || find(nums, target, start + 1, count)
    memo[MemoKey(target, start, count)] = found

    return found
}

fun getSumInt(totalSum: Int, totalCount: Int, count: Int): Int? {

    val tc = totalSum * count
    val sum = tc / totalCount

    return if (sum * totalCount == tc) {
        sum
    } else {
        null
    }
}

fun splitArraySameAverage(nums: IntArray): Boolean {

    val totalSum = nums.sum()

    for (sizeA in 1 until (nums.size / 2) + 1) {
        val sumA = getSumInt(totalSum, nums.size, sizeA)
        sumA ?: continue
        if (find(nums, sumA, 0, sizeA)) {
            return true
        }
    }

    return false
}

fun main() {
    println(splitArraySameAverage(arrayOf(4, 4, 4, 4, 4, 4, 5, 4, 4, 4, 4, 4, 4, 5).toIntArray()))
    //println(splitArraySameAverage(arrayOf(1,2).toIntArray()))
}

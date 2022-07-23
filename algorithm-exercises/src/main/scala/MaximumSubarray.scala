
/*

  Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.

  A subarray is a contiguous part of an array.

  Example 1:
  Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
  Output: 6
  Explanation: [4,-1,2,1] has the largest sum = 6.

  Example 2:
  Input: nums = [1]
  Output: 1

  Example 3:
  Input: nums = [5,4,-1,7,8]
  Output: 23

 */

object MaximumSubarray extends App {
  // Kadane's algorithm
  def maxSubArray(nums: Array[Int]): Int = {
    if (nums.isEmpty) throw new IllegalArgumentException("nums cannot be empty")
    var currSubarray = nums(0)
    var maxSubarray = nums(0)

    println(s"iter 0: $currSubarray $maxSubarray")

    for ((num, iter) <- nums.zipWithIndex.tail) {
      currSubarray = Math.max(num, currSubarray + num)
      maxSubarray = Math.max(maxSubarray, currSubarray)

      println(s"iter $iter: $currSubarray $maxSubarray")
    }

    maxSubarray
  }

  println(maxSubArray(Array(-2,1,-3,4,-1,2,1,-5,4)))
}

import scala.collection.mutable
import scala.util.control.Breaks.{break, breakable}

/*

  Given an integer array nums of length n and an integer target, find three integers in nums such that the sum is closest to target.

  Return the sum of the three integers.

  You may assume that each input would have exactly one solution.

  Example 1:

  Input: nums = [-1,2,1,-4], target = 1
  Output: 2
  Explanation: The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).

  Example 2:

  Input: nums = [0,0,0], target = 1
  Output: 0

 */

object ThreeSumClosest extends App {

  def threeSumClosest(nums: Array[Int], target: Int): Int = {

    var diff: Int = Int.MaxValue
    var tempDiff: Int = 0
    var lo: Int = 0
    var hi: Int = 0
    var tempSum: Int = 0

    println(s"nums = ${nums.mkString(", ")}")
    println(s"target = $target")

    // Sort nums
    val numsSorted: mutable.Buffer[Int] = nums.toBuffer.sorted
    println(s"numsSorted = $numsSorted")

    val lastIdx = numsSorted.length - 1

    breakable {

      for (i <- numsSorted.indices) {

        // Initialize lo and hi for each iteration of i
        lo = i + 1
        hi = lastIdx

        var performWhileLoop = true
        // Only perform is lo < hi
        while (lo < hi) {
          tempSum = numsSorted(i) + numsSorted(lo) + numsSorted(hi)
          tempDiff = tempSum - target

          // Update diff if this tempSum is closer to the target
          if (dist(tempDiff) < dist(diff)) diff = tempDiff

          if (tempSum == target) break
          else if (tempSum < target) lo += 1
          else hi -= 1
        }

        if (diff == 0) break

      }
    }

    target + diff
  }

  def dist(in: Int): Int = Math.abs(in)

  println(s"result = ${threeSumClosest(Array(-6, -3, -1, 2, 3, 5), 0)}\n")
  println(s"result = ${threeSumClosest(Array(-1, 2, 1, -4), 1)}\n")
  println(s"result = ${threeSumClosest(Array(0, 0, 0), 1)}\n")
  println(s"result = ${threeSumClosest(Array(-3, -2, -1, 0, 1, 2, 3, 4), 1)}\n")

}

import scala.collection.{LinearSeq, mutable}
import scala.util.control.Breaks.{break, breakable}

/*

  Write a function to find the longest common prefix string amongst an array of strings.

  If there is no common prefix, return an empty string "".

  Example 1:

  Input: strs = ["flower","flow","flight"]
  Output: "fl"

  Example 2:

  Input: strs = ["dog","racecar","car"]
  Output: ""
  Explanation: There is no common prefix among the input strings.

 */

object LongestCommonPrefix extends App {

  def longestCommonPrefix(strs: Array[String]): String = {

    val strings: mutable.Buffer[List[Char]] = strs.map(_.toList).toBuffer
    val lengths: mutable.Buffer[Int] = strings.map(_.length)
    val iShortest: Int = lengths.indexOf(lengths.min)

    val first = strings(iShortest)
    strings.remove(iShortest)

    val substringMatches = mutable.Buffer.empty[Char]

    breakable {
      for (idx <- first.indices) {
        val charToCheck = first(idx)
        val allMatch: Boolean = strings.forall(l => {
          l(idx) == charToCheck
        })
        if (allMatch) substringMatches += charToCheck
        else {
          break
        }
      }
    }

    if (substringMatches.isEmpty) "(none)"
    else substringMatches.mkString("")

  }

  println(longestCommonPrefix(Array("flower", "flow", "flight")))
  println(longestCommonPrefix(Array("dog","racecar","car")))
  Tuple2
}

import scala.annotation.tailrec

/**
 * Given an integer x, return true if x is palindrome integer.
 *
 * An integer is a palindrome when it reads the same backward as forward.
 *
 * For example, 121 is a palindrome while 123 is not.
 *
 * Example 1:
 *
 * Input: x = 121
 * Output: true
 * Explanation: 121 reads as 121 from left to right and from right to left.
 *
 * Example 2:
 *
 * Input: x = -121
 * Output: false
 * Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.
 *
 * Example 3:
 *
 * Input: x = 10
 * Output: false
 * Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
 *
 */


object IsPalindrome extends App {

  def isPalindrome(num: Int): Boolean = {
    if (num < 0) false
    else if (num < 10) true
    else {
      val charSeq: Seq[Char] = num.toString.toCharArray.toSeq
      val length = charSeq.length
      val (firstHalf: Seq[Char], secondHalf: Seq[Char]) =
        if (length % 2  == 0) {
        charSeq.slice(0, length/2) -> charSeq.slice(length/2, length)
      } else {
          charSeq.slice(0, (length-1)/2) -> charSeq.slice((length+1)/2, length+1)
        }

      checkPalindrome(firstHalf, secondHalf)
    }
  }

  def isPalindromeUsingReverse(num: Int): Boolean = {
    if (num < 0) false
    else if (num < 10) true
    else {
      val charSeq: Seq[Char] = num.toString.toCharArray.toSeq
      charSeq == charSeq.reverse
    }
  }

  @tailrec
  def checkPalindrome(s1: Seq[Char], s2: Seq[Char]): Boolean = {

    val value1 = s1.head
    val value2 = s2.last

    (value1 == value2, s1.tail.isEmpty) match {
      case (true, true) => true
      case (true, false) => checkPalindrome(s1.tail, s2.init)
      case (false, _) => false
    }
  }

  val testCases = Seq(121, -121, 10)

  testCases.foreach(i => println(isPalindrome(i)))

}

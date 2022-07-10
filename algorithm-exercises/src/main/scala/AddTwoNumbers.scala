import scala.annotation.tailrec

/**
 * You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order, and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.
 *
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 *
 * Example 1:
 *
 * Input: l1 = [2,4,3], l2 = [5,6,4]
 * Output: [7,0,8]
 * Explanation: 342 + 465 = 807.
 *
 * Example 2:
 *
 * Input: l1 = [0], l2 = [0]
 * Output: [0]
 *
 * Example 3:
 *
 * Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
 * Output: [8,9,9,9,0,0,0,1]
 *
 */


object AddTwoNumbers extends App {

  def addTwoNumbers(l1: List[Int], l2: List[Int]): List[Int] = {
    if (l1.length != l2.length) throw new IllegalArgumentException("input lists must be the same length")

    addTwoNumbersRec(l1, l2)
  }

  @tailrec
  def addTwoNumbersRec(l1: List[Int], l2: List[Int], sumList: List[Int] = Nil, carry: Int = 0): List[Int] = {
    (l1, l2, carry) match {
      case (l1head :: l1tail, l2head :: l2tail, _) =>
        val sum = (l1head + l2head + carry) % 10
        val newCarry = if (l1head + l2head + carry > 9) 1 else 0
        addTwoNumbersRec(l1tail, l2tail, sumList :+ sum, newCarry)
      case (_, _, 1) => sumList :+ 1
      case _ => sumList
    }
  }

  println(addTwoNumbers(List(2, 4, 3), List(5, 6, 4)))

}

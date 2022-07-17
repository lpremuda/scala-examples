import scala.annotation.tailrec
import scala.collection.mutable

/*

  Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

  Example 1:

  Input: n = 3
  Output: ["((()))","(()())","(())()","()(())","()()()"]

  Example 2:

  Input: n = 1
  Output: ["()"]

  Constraints:

  1 <= n <= 8

 */

object GenerateParenthesesDynamic extends App {

  def generateParenthesis(n: Int): List[String] = {

    val parens: mutable.Buffer[List[String]] = mutable.Buffer.fill(n+1)(List.empty[String])
    parens.update(1, List("()"))

    for (i <- Range.inclusive(2, n)) {
      println(s"i=$i")
      for (k <- Range(0, i)) {
        println(s"k=$k")
        val left: List[String] = Option(parens(i-k-1)).filter(_.nonEmpty).getOrElse(List("")).map(s => s"($s)")
        println(s"\tleft=$left")
        val right: List[String] = parens(k)
        println(s"\tright=$right")
        val newParens: List[String] = {
          if (right.isEmpty) left
          else if (left.isEmpty) right
          else {
            for {
              l <- left
              r <- right
            } yield l + r
          }
        }
        // get current List[String
        val currList = parens(i)
        val updatedList = currList ++ newParens
        parens.update(i, updatedList)
      }
      println(s"parens($i)=${parens(i)}")
    }


    parens(n)

  }

  println()
  println(s"final output = ${generateParenthesis(3)}")
  println(s"final output = ${generateParenthesis(4)}")


}

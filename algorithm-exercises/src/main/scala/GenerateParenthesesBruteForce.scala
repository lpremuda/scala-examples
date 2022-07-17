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

object GenerateParenthesesBruteForce extends App {
  val ONE = "()"
  val start: mutable.Buffer[String] = mutable.Buffer.empty[String]

  def generateParenthesis(n: Int): List[String] = {
    genRec(Array.fill(2 * n)('A'), 0, start).toList
  }

  def generateParenthesisBacktrack(n: Int): List[String] = {
    genRecBacktrack(Array.fill(2 * n)('A'), pos = 0, open = 0, close = 0, max = n, start).toList
  }

  def genRec(curr: Array[Char], pos: Int, output: mutable.Buffer[String]): mutable.Buffer[String] = {
    if (debug) println(s"pos = $pos")
    if (debug) println(s"output = $output")
    if (pos == curr.length) {
      if (debug) println(s"isValid ${isValid(curr)}")
      if (isValid(curr)) output += curr.mkString("")
    } else {
      curr.update(pos, '(')
      if (debug) println(curr.mkString("Array(", ", ", ")"))
      genRec(curr, pos + 1, output)
      curr.update(pos, ')')
      if (debug) println(curr.mkString("Array(", ", ", ")"))
      genRec(curr, pos + 1, output)
    }

    output
  }

  def isValid(curr: Array[Char]): Boolean = {
    var balance: Int = 0
    for (ch <- curr) {
      // Calculate balance
      if (ch == '(') balance += 1
      else balance -= 1

      if (balance < 0) return false
    }

    if (debug) println(s"balance = $balance")
    balance == 0
  }

  def genRecBacktrack(curr: Array[Char], pos: Int, open: Int, close: Int, max: Int, output: mutable.Buffer[String]): mutable.Buffer[String] = {
    if (pos == max * 2) output += curr.mkString("")
    else {
      if (open < max) {
        curr.update(pos, '(')
        genRecBacktrack(curr, pos+1, open+1, close, max, output)
      }

      if (close < open) {
        curr.update(pos, ')')
        genRecBacktrack(curr, pos+1, open, close+1, max, output)
      }

      output
    }
  }

  /*

    genRecBacktrack(,0,0,0,3,)
        genRecBacktrack([,1,1,0,3,)
            genRecBacktrack([[,2,2,0,3,)
                genRecBacktrack([[[,3,3,0,3,)
                    genRecBacktrack([[[],4,3,1,3,)
                        genRecBacktrack([[[]],5,3,2,3,)
                            genRecBacktrack([[[]]],6,3,3,3,)    [[[]]]

                genRecBacktrack([[],3,2,1,3,)
                    genRecBacktrack([[][,4,3,1,3,)
                        genRecBacktrack([[][],5,3,2,3,)
                            genRecBacktrack([[][]],6,3,3,3,)    [[][]]
                    genRecBacktrack([[]],4,2,2,3,)
                        genRecBacktrack([[]][,5,3,2,3,)
                            genRecBacktrack([[]][],6,3,3,3,)    [[]][]
            genRecBacktrack([],2,1,1,3,)
                genRecBacktrack([][,3,2,1,3,)
                    genRecBacktrack([][[,4,3,1,3,)
                        genRecBacktrack([][[],5,3,2,3,)
                            genRecBacktrack([][[]],6,3,3,3,)    [][[]]
                    genRecBacktrack([][],4,2,2,3,)
                        genRecBacktrack([][][,5,3,2,3,)
                            genRecBacktrack([][][],6,3,3,3,)    [][][]



    (
    ((
    ((( 3,0
    ((()
    ((())
    ((()))  5,3,3
    ((())   4,3,2






   */

    val debug = false
//  println(generateParenthesis(0))
//  println(generateParenthesis(1))
//  println(generateParenthesis(2))
//  println(generateParenthesis(3))
//  println(generateParenthesis(4).sorted)
//  val expected = List("(((())))","((()()))","((())())","((()))()","(()(()))","(()()())","(()())()","(())(())","(())()()","()((()))","()(()())","()(())()","()()(())","()()()()")
//  println(expected.sorted)


  println(generateParenthesisBacktrack(1))
  println(generateParenthesisBacktrack(3))
}

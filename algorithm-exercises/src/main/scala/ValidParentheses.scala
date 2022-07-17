import scala.collection.mutable
import scala.util.control.Breaks.{break, breakable}

/*

  Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.

  An input string is valid if:

  Open brackets must be closed by the same type of brackets.
  Open brackets must be closed in the correct order.

  Example 1:

  Input: s = "()"
  Output: true
  Example 2:

  Input: s = "()[]{}"
  Output: true
  Example 3:

  Input: s = "(]"
  Output: false

 */

object ValidParentheses extends App {

  def isValid(s: String): Boolean = {
    val parentheses: Map[Char, Char] = Map(
      '(' -> ')',
      '[' -> ']',
      '{' -> '}'
    )

    val keys: List[Char] = parentheses.keys.toList
    val values: List[Char] = parentheses.values.toList

    val stack: mutable.Stack[Char] = mutable.Stack.empty[Char]

    var valid = true

    breakable {
      for (ch <- s.toCharArray) {
        if (keys.contains(ch)) stack.push(parentheses(ch))
        else if (values.contains(ch)) {
          if (stack.isEmpty) {
            valid = false
            break
          }
          else if (stack.pop != ch) {
            valid = false
            break
          }
        }
      }
    }

    valid && stack.isEmpty

  }

  println(isValid("()"))
  println(isValid("()[]{}"))
  println(isValid("(]"))
  println(isValid("()[]{"))
  println(isValid("({[abc]})"))
  println(isValid("]"))

}

import scala.annotation.tailrec

// WORK IN PROGRESS

object RegularExpressionMatching extends App {



  def isMatch(s: String, p: String): Boolean = {
    recFunc(s.toCharArray, None, p.toCharArray).isEmpty
  }

  @tailrec
  def recFunc(s: Array[Char], maybeWildcard: Option[Char], p: Array[Char]): Array[Char] = {
    if (p.isEmpty) s
    else if (s.isEmpty && p.length==1 && p(0)=='*') s
    else if (s.isEmpty && p.length>1) Array('n', 'o', 't')
    else {
      maybeWildcard match {
        // if wildcard exists
        case Some(wildcard) =>
          // if match on wildcard
          if (s.head == wildcard || wildcard == '.') recFunc(s.tail, maybeWildcard, p)
          // else, get next p and compare, p.tail removes '*'
          else recFunc(s, None, p.tail)
        case None =>
          val newWildcard: Option[Char] = p.toList.tail match {
            case '*' :: tail => Some(p.head)
            case _ => None
          }

          if (s.head == p.head || p.head == '.') recFunc(s.tail, newWildcard, p.tail)
          else recFunc(Array('n', 'o', 't'), None, Array.empty[Char])
      }

    }

  }

  println(isMatch("aa", "a"))
  println(isMatch("aa", "a*"))
  println(isMatch("aaaa", "a*"))
  println(isMatch("aaaab", "a*b"))
  println(isMatch("aaaabb", "a*bb"))
  println(isMatch("ad", "a."))
  println(isMatch("adeee", "a.e*"))
  println(isMatch("abcdsflkjsdf", "abc.*"))
  println(isMatch("abcdsflkjsdf", "ab.*"))
  println(isMatch("abcdsflkjsdf", "a.*"))
  println(isMatch("abcdsflkjsdf", ".*"))

}

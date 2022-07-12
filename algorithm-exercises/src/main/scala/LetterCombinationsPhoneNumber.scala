import scala.collection.mutable

/*

  Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent. Return the answer in any order.

  A mapping of digits to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.

  Example 1:

  Input: digits = "23"
  Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
  Example 2:

  Input: digits = ""
  Output: []
  Example 3:

  Input: digits = "2"
  Output: ["a","b","c"]

 */

object LetterCombinationsPhoneNumber extends App {

  def letterCombinations(digits: String): List[String] = {
    if (digits == "") return List()
    if (digits.length < 5)
      throw new IllegalArgumentException("number of digits must be less than 5")

    lazy val dialpad: Map[String, Seq[String]] = Map(
      "2" -> Seq("a", "b", "c"),
      "3" -> Seq("d", "e", "f"),
      "4" -> Seq("g", "h", "i"),
      "5" -> Seq("j", "k", "l"),
      "6" -> Seq("m", "n", "o"),
      "7" -> Seq("p", "q", "r", "s"),
      "8" -> Seq("t", "u", "v"),
      "9" -> Seq("w", "x", "y", "z")
    )

    // ex. digs = Seq("2", "3", "4")
    val digs: Seq[String] = digits.toIndexedSeq.map(_.toString)
    println(s"digs = $digs")

    val combinations = mutable.Buffer.empty[String]

    def backtrack(index: Int, currentLetterCombo: String): Unit = {

      if (currentLetterCombo.length == digs.length) combinations += currentLetterCombo
      else {
        val digit = digs(index)
        val letters = dialpad(digit)

        for (letter <- letters) {
          val newLetterCombo = currentLetterCombo + letter
          backtrack(index + 1, newLetterCombo)
        }
      }

    }

    backtrack(0, "")

    combinations.toList
  }

  println(letterCombinations("2"))
  println(letterCombinations("23"))
  println(letterCombinations("234"))
  println(letterCombinations("237"))

}

import scala.collection.mutable

/*

  Difficulty: Medium

  Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.

  Symbol       Value
  I             1
  V             5
  X             10
  L             50
  C             100
  D             500
  M             1000
  For example, 2 is written as II in Roman numeral, just two one's added together. 12 is written as XII, which is simply X + II. The number 27 is written as XXVII, which is XX + V + II.

  Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not IIII. Instead, the number four is written as IV. Because the one is before the five we subtract it making four. The same principle applies to the number nine, which is written as IX. There are six instances where subtraction is used:

  I can be placed before V (5) and X (10) to make 4 and 9.
  X can be placed before L (50) and C (100) to make 40 and 90.
  C can be placed before D (500) and M (1000) to make 400 and 900.
  Given an integer, convert it to a roman numeral.

 */

object IntegerToRoman extends App {

  def intToRoman(num: Int): String = {

    if (num < 1) throw new IllegalArgumentException("num must be 1 or greater")

    val romans: mutable.LinkedHashMap[String, Int] = mutable.LinkedHashMap("M" -> 1000, "D" -> 500, "C" -> 100, "L" -> 50, "X" -> 10, "V" -> 5, "I" -> 1)

    val outputList: List[String] = recFunc(romans, num, Nil)

    outputList.mkString("")
  }

  def recFunc(romans: mutable.LinkedHashMap[String, Int], num: Int, output: List[String], previous: String = ""): List[String] = {
    if (romans.isEmpty) output
    else {
      val r: (String, Int) = romans.head

      // Int division automatically rounds down
      val factor = num / r._2
      val remainder = num % r._2

      val (romanToAdd, valueToSubtract) = (r._1, factor) match {
        case ("I", 4) => "IV" -> 4
        case ("V", 1) if remainder == 4 => "IX" -> 9
        case ("X", 4) => "XL" -> 40
        case ("L", 1) if remainder >= 40 => "XC" -> 90
        case ("C", 4) => "CD" -> 400
        case ("D", 1) if remainder >= 400 => "CM" -> 900
        case _ => Array.fill(factor)(r._1).mkString("") -> factor * r._2
      }

      recFunc(romans.tail, num - valueToSubtract, output :+ romanToAdd, r._1)
    }
  }

  println(intToRoman(3))
  println(intToRoman(58))
  println(intToRoman(1994))
  println(intToRoman(1494))
  println(intToRoman(14))
  println(intToRoman(95))
  println(intToRoman(29))
  println(intToRoman(342))

}

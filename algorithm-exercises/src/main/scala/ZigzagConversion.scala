import scala.collection.mutable

//  Example 1:
//
//  Input: s = "PAYPALISHIRING", numRows = 3
//  Output: "PAHNAPLSIIGYIR"
//  Explanation:
//
//    P   A   H   N
//    A P L S I I G
//    Y   I   R

//  Example 2:
//
//  Input: s = "PAYPALISHIRING", numRows = 4
//  Output: "PINALSIGYAHRPI"
//  Explanation:
//    P     I    N
//    A   L S  I G
//    Y A   H R
//    P     I

object ZigzagConversion extends App {

  def convert(s: String, numRows: Int): String = {
    // Row and column pointers
    var r = 0
    var c = 0

    // Direction pointers are heading
    var down = true

    // Initialize vector of lists
    var seq = mutable.Seq.fill(4)(mutable.Seq.empty[Char])

    // Convert input string to List[Char]
    val listChar: List[Char] = s.toCharArray.toList

    listChar.foreach { ch =>
      val currRow = seq(r)
      val currRowUpdated = currRow :+ ch
      seq.update(r, currRowUpdated)

      // Change directions, if necessary
      if (r == numRows - 1) down = false
      if (r == 0) down = true

      // Update pointers
      r = if (down) r+1 else r-1
    }

    seq.flatten.mkString("")

  }

  println(convert("PAYPALISHIRING", 4))
  println(convert("PAYPALISHIRING", 3))

}

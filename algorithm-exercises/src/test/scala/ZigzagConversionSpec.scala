import IsPalindrome.{isPalindrome, isPalindromeUsingReverse}
import ZigzagConversion.convert
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers._

class ZigzagConversionSpec extends AnyFlatSpec with should.Matchers {

  "isPalindrome method" should "return correct boolean values for corresponding inputs" in {
    assert(convert("PAYPALISHIRING", 4) == "PINALSIGYAHRPI")
    assert(convert("PAYPALISHIRING", 3) == "PAHNAPLSIIGYIR")
  }

}

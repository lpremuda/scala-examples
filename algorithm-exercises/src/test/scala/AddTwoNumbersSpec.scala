import AddTwoNumbers.addTwoNumbers
import IsPalindrome.{isPalindrome, isPalindromeUsingReverse}
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers._

class AddTwoNumbersSpec extends AnyFlatSpec with should.Matchers {

  "addTwoNumbers method" should "returns correct linked list results" in {
    assert(addTwoNumbers(List(2, 4, 3), List(5, 6, 4)) == List(7, 0, 8))
    assert(addTwoNumbers(List(6, 2), List(4, 7)) == List(0, 0, 1))
    assert(addTwoNumbers(List(5, 4, 1), List(2, 9, 5)) == List(7, 3, 7))
  }

}

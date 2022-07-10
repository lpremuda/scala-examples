import IsPalindrome.{isPalindrome, isPalindromeUsingReverse}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest._
import flatspec._
import matchers._
import org.scalatest.Assertions._

class IsPalindromeSpec extends AnyFlatSpec with should.Matchers {

  "isPalindrome method" should "return correct boolean values for corresponding inputs" in {
    assert(isPalindrome(121))
    assert(!isPalindrome(-121))
    assert(!isPalindrome(10))
    assert(isPalindrome(12321))
    assert(isPalindrome(123321))
    assert(isPalindrome(1))
  }

  "isPalindromeUsingReverse method" should "return correct boolean values for corresponding inputs" in {
    assert(isPalindromeUsingReverse(121))
    assert(!isPalindromeUsingReverse(-121))
    assert(!isPalindromeUsingReverse(10))
    assert(isPalindromeUsingReverse(12321))
    assert(isPalindromeUsingReverse(123321))
    assert(isPalindromeUsingReverse(1))
  }

}

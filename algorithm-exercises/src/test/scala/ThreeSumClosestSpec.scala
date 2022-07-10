import ThreeSumClosest.threeSumClosest
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers._

class ThreeSumClosestSpec extends AnyFlatSpec with should.Matchers {

  "threeSumClosest method" should "return sum closest to target value" in {
    assert(threeSumClosest(Array(-6, -3, -1, 2, 3, 5), target = 0) == 1)
    assert(threeSumClosest(Array(-1, 2, 1, -4), target = 1) == 2)
    assert(threeSumClosest(Array(0, 0, 0), target = 1) == 0)
    assert(threeSumClosest(Array(-3, -2, -1, 0, 1, 2, 3, 4), target = 1) == 1)
  }

}

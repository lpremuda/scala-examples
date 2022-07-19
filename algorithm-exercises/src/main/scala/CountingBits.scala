import scala.collection.mutable

object CountingBits extends App {

  def countBits(n: Int): Array[Int] = {
    if (n == 0) Array(0)
    else if (n == 1) Array(0,1)
    else {
      val ones: mutable.Buffer[Int] = mutable.Buffer.fill(n+1)(0)
      ones.update(0, 0)
      ones.update(1, 1)

      var currExponent = 1
      var x = 1

      for (i <- Range.inclusive(2, n)) {
        if (i == Math.pow(2, currExponent).toInt) {
          // power of 2 always has one element
          ones.update(i, 1)
          currExponent += 1
          x = 1
        } else {
          ones.update(i, 1 + ones(x))
          x += 1
        }
      }

      ones.toArray
    }
  }


  println(countBits(2).mkString("Array(", ", ", ")"))
  println(countBits(5).mkString("Array(", ", ", ")"))
}

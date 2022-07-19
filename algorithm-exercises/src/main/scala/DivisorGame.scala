
// WORK IN PROGRESS

object DivisorGame extends App {
  def divisorGame(n: Int): Boolean = {
    numMovesTo1(n) % 2 == 1
  }

  def numMovesTo1(n: Int): Int = {
    if (n == 1) 0
    else if (n == 2) 1
    else {
      1 + numMovesTo1(n - highestFactor(n))
    }
  }

  def highestFactor(n: Int): Int = {
    if (n % 2 == 0) n/2
    else {
      val highestPossibleFactor = Math.floor(n/2).toInt
      for (factor <- Range.inclusive(highestPossibleFactor, 1, -1)) {
        if (n % factor == 0) return factor
      }
      1
    }
  }


  println(divisorGame(2)) // true
  println(divisorGame(3)) // false
  println(divisorGame(4)) // false
  println(divisorGame(5)) // true
  println(divisorGame(6)) // true
  println(divisorGame(1000))
}

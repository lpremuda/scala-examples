object BestTimeBuySellStock extends App {

  def maxProfit(prices: Array[Int]): Int = {

    var maxProf = 0

    for (i <- prices.init.indices) {
      for (j <- Range(i+1, prices.length)) {
        maxProf = Math.max(maxProf, prices(j) - prices(i))
      }
    }

    maxProf

  }

  println(maxProfit(Array(7,1,5,3,6,4)))
  println(maxProfit(Array(7,6,4,3,1)))

}

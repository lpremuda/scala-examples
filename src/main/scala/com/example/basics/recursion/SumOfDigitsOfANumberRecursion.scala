package com.example.basics.recursion

object SumOfDigitsOfANumberRecursion extends App {

  // Example: 142 => 1 + 4 + 2 = 7

  def sumOfDigits(num: Int, acc: Int = 0): Int = {
    num.toString match {
      case c if c.length == 1 => acc + num
      case str => sumOfDigits(str.substring(1).toInt, acc + str.substring(0,1).toInt)
    }
  }

  println(if (sumOfDigits(142) == 7) "Pass" else "Fail")
  println(if (sumOfDigits(5820) == 15) "Pass" else "Fail")


}

package com.example.basics.recursion

import scala.annotation.tailrec

object ExponentRecursion extends App {

  // Example: 2^4 = 16

  def power(base: Int, exp: Int): Int = {
    if (exp <= 0) 1
    else base * power(base, exp-1)
  }

  @tailrec
  def powerTR(base: Int, exp: Int, acc: Int = 1): Int = {
    if (exp <= 0) acc
    else powerTR(base, exp-1, acc * base)
  }

  println(power(2, 5))
  println(powerTR(2, 6))
}

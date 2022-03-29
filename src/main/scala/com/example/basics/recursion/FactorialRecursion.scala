package com.example.basics.recursion

import scala.annotation.tailrec

object FactorialRecursion extends App {

  def factorial(n: Int): Int = {
    if (n <= 1) n
    else n * factorial(n-1)
  }

  @tailrec
  def factorialTR(n: Int, acc: Int = 1): Int = {
    if (n <= 1) acc
    else factorialTR(n-1, n * acc)
  }

//  println(factorial(6))
  println(factorialTR(10))

}

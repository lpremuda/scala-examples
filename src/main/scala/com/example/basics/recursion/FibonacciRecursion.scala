package com.example.basics.recursion

import scala.annotation.tailrec

object FibonacciRecursion extends App {

  // Find the nth number in the Fibonacci sequence

  // 1, 1, 2, 3, 5, 8, 13, 21, 34, 55

  // For n = 6,
  // n=1 =>         1
  // n=2 =>         1
  // n=3 => 1 + 1 = 2
  // n=4 => 1 + 2 = 3
  // n=5 => 2 + 3 = 5
  // n=6 => 3 + 5 = 8

  def findFibonacci(n: Int): Int = {
    val a = 0
    val b = 1
    fibonacci(n, a, b)
  }

  @tailrec
  def fibonacci(n: Int, a: Int = 0, b: Int = 1): Int = {

    if (n == 1) b
    else fibonacci(n-1, b, a+b)
  }

  // Test
  (1 to 7)
    .map(x => findFibonacci(x))
    .foreach(x => print(s"$x "))

  /*
                                                                                                                                                    findFibonacci(7, 0, 1)
                                                                                                                            findFibonacci(6, 0, 1)  findFibonacci(6, 1, 1)
                                                                                                    findFibonacci(5, 0, 1)  findFibonacci(5, 1, 1)  findFibonacci(5, 1, 2)
                                                                            findFibonacci(4, 0, 1)  findFibonacci(4, 1, 1)  findFibonacci(4, 1, 2)  findFibonacci(4, 2, 3)
                                                    findFibonacci(3, 0, 1)  findFibonacci(3, 1, 1)  findFibonacci(3, 1, 2)  findFibonacci(3, 2, 3)  findFibonacci(3, 3, 5)
                            findFibonacci(2, 0, 1)  findFibonacci(2, 1, 1)  findFibonacci(2, 1, 2)  findFibonacci(2, 2, 3)  findFibonacci(2, 3, 5)  findFibonacci(2, 5, 8)
    findFibonacci(1, 0, 1)  findFibonacci(1, 1, 1)  findFibonacci(1, 1, 2)  findFibonacci(1, 2, 3)  findFibonacci(1, 3, 5)  findFibonacci(1, 5, 8)  findFibonacci(1, 8, 13)
   */

  /*

   */


}

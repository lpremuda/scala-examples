package com.example.basics.recursion

import scala.annotation.tailrec

object FibonacciRecursion extends App {

  /*

    Problem: Find the nth number in the Fibonacci sequence

 iter6                                                                   prevTotal    total
 iter5                                                      prevTotal    total
 iter4                                         prevTotal    total
 iter3                            prevTotal    total
 iter2               prevTotal    total
 iter1  prevTotal    total
 fib    0            1            1            2            3            5            8            13            21            34            55
 n      0            1            2            3            4            5            6            7             8             9             10

   */

  @tailrec
  def fibonacci(n: Int, prevTotal: Int = 0, total: Int = 1): Int = {
    if (n == 1) total
    else fibonacci(n-1, total, prevTotal + total)
  }

  // Test
  val fibInput = 1 to 7
  val fibOutput = fibInput.map(x => fibonacci(x))
  val inOutZipped: IndexedSeq[(Int, Int)] = fibInput.zip(fibOutput)
  inOutZipped.foreach(tuple => println(s"${tuple._1} -> ${tuple._2}"))

  /*

    Time complexity: O(n)
                                                                                                                            fibonacci(7, 0, 1)
                                                                                                        fibonacci(6, 0, 1)  fibonacci(6, 1, 1)
                                                                                    fibonacci(5, 0, 1)  fibonacci(5, 1, 1)  fibonacci(5, 1, 2)
                                                                fibonacci(4, 0, 1)  fibonacci(4, 1, 1)  fibonacci(4, 1, 2)  fibonacci(4, 2, 3)
                                            fibonacci(3, 0, 1)  fibonacci(3, 1, 1)  fibonacci(3, 1, 2)  fibonacci(3, 2, 3)  fibonacci(3, 3, 5)
                        fibonacci(2, 0, 1)  fibonacci(2, 1, 1)  fibonacci(2, 1, 2)  fibonacci(2, 2, 3)  fibonacci(2, 3, 5)  fibonacci(2, 5, 8)
    fibonacci(1, 0, 1)  fibonacci(1, 1, 1)  fibonacci(1, 1, 2)  fibonacci(1, 2, 3)  fibonacci(1, 3, 5)  fibonacci(1, 5, 8)  fibonacci(1, 8, 13)

  bst of "n" with n = 6:
                                                                6
                                 5                                                             4
                 4                             3                               3                                2
          3             2               2             1                 2             1                  1             0
       2     1       1     0         1     0                         1     0
     1   0

  bst of "total" with n = 6:
                                                                8
                                 5                                                             3
                 3                             2                               2                                1
          2             1               1             1                 1             1                  1             0
       1     1       1     0         1     0                         1     0
     1   0

  Illustrations for how the fibonacci tail recursion traverses through the binary tree

  Legend:
    t = "total" variable
    p = "prevTotal" variable

  bst of "total" with n = 6, iteration 1: fibonacci(6, p=0, t=1):
                                                                8
                                 5                                                             3
                 3                             2                               2                                1
          2             1               1             1                 1             1                  1             0
       1     1       1     0         1     0                         1     0
    t1  p0

  bst of "total" with n = 6, iteration 2: fibonacci(5, p=1, t=1):
                                                                8
                                 5                                                             3
                 3                             2                               2                                1
          2             1               1             1                 1             1                  1             0
      t1    p1       1     0         1     0                         1     0
     1   0

  bst of "total" with n = 6, iteration 3: fibonacci(4, p=1, t=2):
                                                                8
                                 5                                                             3
                 3                             2                               2                                1
         t2            p1               1             1                 1             1                  1             0
       1     1       1     0         1     0                         1     0
     1   0

  bst of "total" with n = 6, iteration 4: fibonacci(3, p=2, t=3):
                                                                8
                                 5                                                             3
                t3                            p2                               2                                1
          2             1               1             1                 1             1                  1             0
       1     1       1     0         1     0                         1     0
     1   0

  bst of "total" with n = 6, iteration 5:  fibonacci(2, p=3, t=5):
                                                                8
                                t5                                                            p3
                 3                             2                               2                                1
          2             1               1             1                 1             1                  1             0
       1     1       1     0         1     0                         1     0
     1   0

  bst of "total" with n = 6, iteration 5:  fibonacci(1, p=5, t=8):
  if (n == 1) t
                                                               t8                                              for illustration purposes -->  p5
                                 5                                                             3
                 3                             2                               2                                1
          2             1               1             1                 1             1                  1             0
       1     1       1     0         1     0                         1     0
     1   0

   */



}

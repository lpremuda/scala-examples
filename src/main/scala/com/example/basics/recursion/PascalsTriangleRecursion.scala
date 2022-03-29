package com.example.basics.recursion

import scala.annotation.tailrec

object PascalsTriangleRecursion extends App {

  //         1
  //       1   1
  //     1   2   1
  //   1   3   3   1
  // 1   4   6   4   1

  @tailrec
  def pascal(n: Int, acc: Seq[Seq[Int]] = Seq.empty[Seq[Int]], counter: Int = 1): Seq[Seq[Int]] = {
    if (counter == 1) pascal(n-1, acc :+ Seq(1), counter + 1)
    else if (counter == 2) pascal(n-1, acc :+ Seq(1,1), counter + 1)
    else {
      if (n <= 0) acc
      else {
        val newLine: Seq[Int] = 1 +: sumLine(acc(counter-2)) :+ 1
        pascal(n-1, acc :+ newLine, counter + 1)
      }
    }
  }

  @tailrec
  def sumLine(line: Seq[Int], acc: Seq[Int] = Seq.empty[Int]): Seq[Int] = {
    line match {
      case head :: middle :: tail => sumLine(middle :: tail, acc :+ head + middle)
      case _ => acc
    }
  }

  // For debugging
  println(sumLine(Seq(1,3,3,1)))

  val pascalsTriangle: Seq[Seq[Int]] = pascal(7)
  pascalsTriangle.foreach(println)

}

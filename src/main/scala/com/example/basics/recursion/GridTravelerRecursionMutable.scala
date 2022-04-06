package com.example.basics.recursion

import scala.collection.mutable

object GridTravelerRecursionMutable extends App {

  /*

    Problem Statement:
      Given the following scenario:
        1. You are at the top-left corner of an m x n grid.
        2. You can only move down or to the right.
        3. Find the number of paths you can take to get to the bottom-right corner of the grid.

   */

  def gridTraveler(m: Long, n: Long, memo: mutable.Map[String, Long] = mutable.Map.empty[String, Long]): Long = {
    val key: String = s"$m,$n"
    memo.get(key) match {
      case Some(value) => value
      case None        =>
        // If m or n equals 0, you are out of bounds, so return 0 signifying this is not a valid path
        if (m == 0 || n == 0) 0
        // If you reach grid (1,1), you are at the bottom-right corner, so return 1 to signify you have found a valid path
        else if (m == 1 && n == 1) 1
        // gridTraveler(m - 1, n) means move down 1
        // gridTraveler(m, n - 1) means move to the right 1
        else {
          memo.addOne(key -> (gridTraveler(m - 1, n, memo) + gridTraveler(m, n - 1, memo)))
          memo(key)
        }
    }
  }

  println(gridTraveler(1, 2)) // 1
  println(gridTraveler(2, 1)) // 1
  println(gridTraveler(2, 2)) // 2
  println(gridTraveler(2, 3)) // 3
  println(gridTraveler(3, 2)) // 3
  println(gridTraveler(3, 3)) // 6
  println(gridTraveler(18, 18)) // 2333606220

}

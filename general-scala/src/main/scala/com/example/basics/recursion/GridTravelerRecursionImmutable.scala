package com.example.basics.recursion

object GridTravelerRecursionImmutable extends App {

  /*

    Problem Statement:
      Given the following scenario:
        1. You are at the top-left corner of an m x n grid.
        2. You can only move down or to the right.
        3. Find the number of paths you can take to get to the bottom-right corner of the grid.

   */

  val debug = true

  def gridTraveler(m: Long, n: Long, memo: Map[String, Long] = Map.empty[String, Long]): (Long, Map[String, Long]) = {
    val key: String = s"$m,$n"
    if (debug) println(s"key=$key")
    memo.get(key) match {
      case Some(value) => value -> memo
      case None        =>
        // If m or n equals 0, you are out of bounds, so return 0 signifying this is not a valid path
        if (m == 0 || n == 0) 0.toLong -> memo
        // If you reach grid (1,1), you are at the bottom-right corner, so return 1 to signify you have found a valid path
        else if (m == 1 && n == 1) 1.toLong -> memo
        // gridTraveler(m - 1, n) means move down 1
        // gridTraveler(m, n - 1) means move to the right 1
        else {
          val (valBottom, memoBottom) = gridTraveler(m - 1, n, memo)
          val (valRight, memoRight) = gridTraveler(m, n - 1, memoBottom)
          val (valNew, memoNew) = (valBottom + valRight) -> memoRight
          val memoNew2 = memoNew + (key -> valNew)
          valNew -> memoNew2
        }
    }
  }

  def gridTravelerCaller(m: Long, n: Long): Long = {
    val result: (Long, Map[String, Long]) = gridTraveler(m, n)
    result._1
  }

//  println(gridTravelerCaller(1, 2)) // 1
//  println(gridTravelerCaller(2, 1)) // 1
//  println(gridTravelerCaller(2, 2)) // 2
//  println(gridTravelerCaller(2, 3)) // 3
//  println(gridTravelerCaller(3, 2)) // 3
  println(gridTravelerCaller(3, 3)) // 6
//  println(gridTravelerCaller(18, 18)) // 2333606220

  /*

    gridTraveler(3, 3, [])
      gridTraveler(2, 3, [])
        gridTraveler(1, 3, [])
          gridTraveler(0, 3, []) 0 -> []
          gridTraveler(1, 2, [])
            (0, []) = gridTraveler(0, 2, [])
            (1, []) = gridTraveler(1, 1, [])
            (0 + 1) -> []


   */

}

package com.example.basics.recursion

object LeastCommonMultipleRecursion extends App {

  // Example 2 and 5 => 10
  // 2, 4, 6, 8, *10*, 12
  // 5, *10*, 15, 20, 25

  //
  var i = -1
  def LCM(a: Int, b: Int): Int = {
    i += 1
    println(s"iteration $i")
    println(s"a = $a")
    println(s"b = $b")
    val remainder = a % b
    println(s"remainder = $remainder")
    if (remainder == 0) a
    else a * LCM(b, remainder) / remainder
  }



  println(LCM(4,3))

  // LCM(2,5)
  // 2 * ( 5 * [ 2 ] / 1 ) / 2

  // LCM(7,5)
  // 7 * ( 5 * [ 2 ] / 1 ) / 2

  // LCM(4,3)
  // 4 * ( 3 ) / 1

}

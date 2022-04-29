package com.example.basics.recursion

import scala.annotation.tailrec

object ReverseAStringRecursion extends App {

  def reverse(str: String): String = {
    if (str.length <= 1) str
    else str.substring(str.length-1) + reverse(str.substring(0,str.length-1))
  }

  @tailrec
  def reverseTR(str: String, acc: String = ""): String = {
    if (str.length <= 0) acc
    else reverseTR(str.substring(0,str.length-1), acc + str.substring(str.length-1))
  }

  println(reverse("Hello!"))
  println(reverse(""))
  println(reverseTR("Hello!"))
  println(reverseTR(""))

}

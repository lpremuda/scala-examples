package com.example.basics.lazyval

object LazyValExample extends App {

  /*

      Output if value is val:
          BREAK 0
          hello value
          BREAK 1
          returns value
          BREAK 2
          returns value
          BREAK 3
          returns value
          BREAK 4

      Output if value is lazy val:
          BREAK 0
          BREAK 1
          hello value
          returns value
          BREAK 2
          returns value
          BREAK 3
          returns value
          BREAK 4


      Output if value is def:
          BREAK 0
          BREAK 1
          hello value
          returns value
          BREAK 2
          hello value
          returns value
          BREAK 3
          hello value
          returns value
          BREAK 4
   */

  println("BREAK 0")
  val value = {
    println("hello value")
    "returns value"
  }

  println("BREAK 1")
  println(value)

  println("BREAK 2")
  println(value)

  println("BREAK 3")
  println(value)

  println("BREAK 4")

}

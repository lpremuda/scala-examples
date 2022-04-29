package com.example.cats.kleislis

object MotivationForKleislis extends App {

  // Motivation: In this simple example, function composition is easy when
  // using primitive data types and monadic types, such as List, Option, etc...

  // compiled as: val double: Function1[Int,Int] = x => x * 2
  val double: Int => Int = x => x * 2
  val intToCats: Int => String = x => {
    if (x == 1) s"$x cat"
    else s"$x cats"
  }

  println(double(3))
  println(intToCats(1))
  println(intToCats(4))

  val doubleTheCatsAndThen: Int => String = double.andThen(intToCats)
  println(doubleTheCatsAndThen(5))
  val doubleTheCatsCompose: Function1[Int,String] = intToCats.compose(double)
  println(doubleTheCatsCompose(5))

}

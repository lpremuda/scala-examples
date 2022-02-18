package com.example.cats.semigroups

//import cats.kernel.instances.string._
//import cats.kernel.instances.int._
//import cats.kernel.instances.option._

import cats.Semigroup

object SemigroupCatsExample extends App {

  println(Semigroup[String].combine("Hello ", "World!"))

  println(Semigroup[Option[Int]].combine(None, Some(1)))
}

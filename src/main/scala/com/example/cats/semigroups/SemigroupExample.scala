package com.example.cats.semigroups

trait Semigroup[A] {
  def combine(x: A, y: A): A
}

object SemigroupInstances {
  implicit val intSemigroup = new Semigroup[Int] {
    def combine(x: Int, y: Int): Int = x + y
  }
  implicit val stringSemigroup = new Semigroup[String] {
    def combine(x: String, y: String): String = x ++ y
  }
}

object SemigroupExample extends App {
  def combine[A](x: A, y: A)(implicit semigroup: Semigroup[A]): A = {
    semigroup.combine(x,y)
  }
  import SemigroupInstances._
  println(intSemigroup.combine(2, 5))
  println(combine(3,8))
  println(combine("Hello", " World!"))
}

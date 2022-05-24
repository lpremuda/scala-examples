package com.example.lectures.youtube.typelevel.scalaworkforyou.part1

// From Youtube video "Let the Scala compiler work for you - Adelbert Chang" on Youtube channel, Typelevel

// Typeclass definition
trait Compute[A] {
  def compute(a: A): Int
}

object Compute {
  implicit val computeInt: Compute[Int] = new Compute[Int] {
    def compute(a: Int): Int = a
  }

  implicit val computeString: Compute[String] = new Compute[String] {
    def compute(a: String): Int = a.length
  }

  implicit def computeList[A](implicit A: Compute[A]): Compute[List[A]] = new Compute[List[A]] {
    def compute(a: List[A]): Int = a.map(A.compute).foldLeft(0)(_ + _)
  }
}

object ComputeApp extends App {
  def go[A](a: A)(implicit A: Compute[A]): Int = {
    A.compute(a)
  }

  println(go(8))                                // 8
  println(go("Lucas"))                          // 5
  println(go(List(2,5,7)))                      // 14
  println(go(List("Lucas", "is", "awesome!")))  // 15
}
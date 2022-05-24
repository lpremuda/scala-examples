package com.example.lectures.youtube.typelevel.scalaworkforyou.part2

// From Youtube video "Let the Scala compiler work for you - Adelbert Chang" on Youtube channel, Typelevel

// Typeclass definition
trait Compute[A] {
  type Out

  def compute(a: A): Out
}

object Compute {
  // { type Out = Double } isn't necessary, but is included to show that the code still compiles
  // Note: Not including { type Out = Double } also compiles
  implicit val computeInt: Compute[Int] { type Out = Double } = new Compute[Int] {
    type Out = Double

    def compute(a: Int): Double = 1.0 / a
  }

  // { type Out = Option[Char] } isn't necessary, but is included to show that the code still compiles
  // Not including { type Out = Option[Char] } also compiles
  implicit val computeString: Compute[String] { type Out = Option[Char] } = new Compute[String] {
    type Out = Option[Char]

    def compute(a: String): Option[Char] = a.headOption
  }
}

object ComputeApp extends App {
  // Path dependent type, A.Out
  def go[A](a: A)(implicit A: Compute[A]): A.Out = {
    A.compute(a)
  }

  println(go(8))        // 0.125
  println(go("Lucas"))  // Some("L")
}
package com.example.lectures.youtube.scalaworld.typeclassinduction

import com.example.cats.overview.CatsTypeClassesOverview.Monad

trait Squishy[A] {
  def squish(a1: A, a2: A): A
}

object Squishy {
  implicit val squishyInt = new Squishy[Int] {
    def squish(a1: Int, a2: Int): Int = a1 + a2
  }

  implicit def squishyOption[A](implicit A: Squishy[A]): Squishy[Option[A]] = new Squishy[Option[A]] {
    def squish(a1: Option[A], a2: Option[A]): Option[A] = {
      for {
        a1val <- a1
        a2val <- a2
      } yield A.squish(a1val, a2val)
    }
  }

  implicit def squishyList[A](implicit A: Squishy[A]): Squishy[List[A]] = new Squishy[List[A]] {
    def squish(a1: List[A], a2: List[A]): List[A] = {
      for {
        a1val <- a1
        a2val <- a2
      } yield A.squish(a1val, a2val)
    }
  }

  // <- isn't recognized
//  implicit def squishyWrapper[F[_] : Monad, A](implicit A: Squishy[A]): Squishy[F[A]] = new Squishy[F[A]] {
//    def squish(a1: F[A], a2: F[A]): F[A] = {
//      for {
//        a1val <- a1
//        a2val <- a2
//      } yield A.squish(a1val, a2val)
//    }
//  }
}

object SquishyApp extends App {

  def go[A](x: A, y: A)(implicit A: Squishy[A]): A = {
    A.squish(x, y)
  }

  println(go(3, 5))                   // 8
  println(go(Option(3), Option(5)))   // Some(8)
  println(go(List(1,5), List(3,4)))   // List(4, 5, 8, 9)

}
